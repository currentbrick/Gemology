package com.currentbrick.gemology.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.currentbrick.gemology.entity.gem.GemStats;
import com.currentbrick.gemology.init.ModEntities;
import com.currentbrick.gemology.item.FusionItem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityFusion extends Monster implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private CompoundTag firstGemData;
    private CompoundTag secondGemData;

    private static final EntityDataAccessor<String> FUSION_GEM_1 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FUSION_GEM_2 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Long> FUSION_ID = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.LONG);

    private GemStats fusionStats;
    private GemDimensions fusionDimensions;

    private List<Identifier> fusionAbilities = new ArrayList<>();

    private static final EntityDataAccessor<Float> FUSION_WIDTH = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> FUSION_HEIGHT = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.FLOAT);

    public EntityFusion(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(FUSION_WIDTH, 1F);
        builder.define(FUSION_HEIGHT, 2F);

        builder.define(FUSION_GEM_1, "");
        builder.define(FUSION_GEM_2, "");
        builder.define(FUSION_ID, 0L);
    }

    public void setComponents(CompoundTag first, CompoundTag second) {
        this.firstGemData = first;
        this.secondGemData = second;

        Identifier gem1 = getGemId(first);
        Identifier gem2 = getGemId(second);

        entityData.set(FUSION_GEM_1, gem1 != null ? gem1.toString() : "");
        entityData.set(FUSION_GEM_2, gem2 != null ? gem2.toString() : "");
    }

    private Identifier getGemId(CompoundTag data) {
        if (data == null) {
            return null;
        }

        String gemType = data.getString("GemType").orElse(null);

        return gemType != null ? Identifier.tryParse(gemType) : null;
    }

    public CompoundTag getFirstGemData() {
        return firstGemData;
    }

    public CompoundTag getSecondGemData() {
        return secondGemData;
    }

    public GemStats getFusionStats() {
        return fusionStats;
    }


    public Identifier getGem1ID() {
        String id = entityData.get(FUSION_GEM_1);

        return id.isEmpty() ? null : Identifier.tryParse(id);
    }

    public Identifier getGem2ID() {
        String id = entityData.get(FUSION_GEM_2);

        return id.isEmpty() ? null : Identifier.tryParse(id);
    }

    public void setFusionStats(GemStats fusionStats) {
        this.fusionStats = fusionStats;
        applyStats();
    }

    public List<Identifier> getFusionAbilities() {
        return fusionAbilities;
    }

    public void setFusionAbilities(List<Identifier> fusionAbilities) {
        this.fusionAbilities = new ArrayList<>(fusionAbilities);
    }

    public GemDimensions getFusionDimensions() {
        return new GemDimensions(entityData.get(FUSION_WIDTH), entityData.get(FUSION_HEIGHT));
    }

    public void setFusionDimensions(GemDimensions fusionDimensions) {
        this.fusionDimensions = fusionDimensions;

        this.entityData.set(FUSION_WIDTH, fusionDimensions.getWidth());
        this.entityData.set(FUSION_HEIGHT, fusionDimensions.getHeight());

        refreshDimensions();

        System.out.println(
                (level().isClientSide() ? "CLIENT" : "SERVER") +
                        " BOUNDING BOX: " +
                        getBoundingBox().getXsize() + " x " +
                        getBoundingBox().getYsize()
        );
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key.equals(FUSION_WIDTH) || key.equals(FUSION_HEIGHT)) {
            refreshDimensions();
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        if (fusionStats != null) {
            output.putFloat("Health", fusionStats.getHealth());
            output.putFloat("Strength", fusionStats.getStrength());
            output.putFloat("Speed", fusionStats.getSpeed());
        }

        output.putFloat("FusionWidth", entityData.get(FUSION_WIDTH));
        output.putFloat("FusionHeight", entityData.get(FUSION_HEIGHT));

        if (firstGemData != null) {
            output.store("FirstGem", CompoundTag.CODEC, firstGemData);
        }

        if (secondGemData != null) {
            output.store("SecondGem", CompoundTag.CODEC, secondGemData);
        }

        output.putLong("FusionID", entityData.get(FUSION_ID));
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        float health = input.getFloatOr("Health", 0.0F);
        float strength = input.getFloatOr("Strength", 0.0F);
        float speed = input.getFloatOr("Speed", 0.0F);

        float width = input.getFloatOr("FusionWidth", 1.0F);

        float height = input.getFloatOr("FusionHeight", 2.0F);

        fusionDimensions = new GemDimensions(width, height);

        entityData.set(FUSION_WIDTH, width);

        entityData.set(FUSION_HEIGHT, height);

        firstGemData = input.read("FirstGem", CompoundTag.CODEC).orElse(null);

        secondGemData = input.read("SecondGem", CompoundTag.CODEC).orElse(null);

        if (health != 0.0F || strength != 0.0F || speed != 0.0F) {
            fusionStats = new GemStats(health, strength, speed);
        }

        if (fusionStats != null) {
            applyStats();
        }

        Identifier gem1 = getGemId(firstGemData);
        Identifier gem2 = getGemId(secondGemData);

        entityData.set(FUSION_GEM_1, gem1 != null ? gem1.toString() : "");
        entityData.set(FUSION_GEM_2, gem2 != null ? gem2.toString() : "");
        entityData.set(FUSION_ID, entityData.get(FUSION_ID));
    }

    public void applyStats() {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(fusionStats.getHealth());
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(fusionStats.getSpeed());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(fusionStats.getStrength());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        if (level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = player.getItemInHand(hand);

        if (!(stack.getItem() instanceof FusionItem)) {
            return InteractionResult.PASS;
        }

        if (firstGemData == null || secondGemData == null) {
            return InteractionResult.PASS;
        }

        double offset = 1;

        Vec3 direction = player.getLookAngle();
        direction = new Vec3(direction.x, 0, direction.z).normalize();
        Vec3 side = new Vec3(-direction.z, 0, direction.x).normalize();

        spawnGem(firstGemData, (-side.x * offset), 0, (-side.z * offset));
        spawnGem(secondGemData, (side.x * offset), 0, (side.z * offset));

        discard();

        return InteractionResult.SUCCESS;
    }

    private void spawnGem(CompoundTag gemData, double offsetX, double offsetY, double offsetZ) {
        EntityGem gem = ModEntities.GEM.get().create(level(), EntitySpawnReason.SPAWN_ITEM_USE);

        if (gem == null) {
            return;
        }

        gem.applyGemData(gemData);

        gem.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);

        level().addFreshEntity(gem);
    }

    public void setFusionId(Long id) {
        entityData.set(FUSION_ID, id);
    }

    public Long getFusionId() {
        return entityData.get(FUSION_ID);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return EntityDimensions.scalable(
                entityData.get(FUSION_WIDTH),
                entityData.get(FUSION_HEIGHT)
        );
    }

    @Override
    public boolean isPickable() {
        System.out.println(
                "PICKABLE BOX: " +
                        getBoundingBox().getXsize() + " x " +
                        getBoundingBox().getYsize()
        );

        return super.isPickable();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(Gemology.FUSION_NAME_MANAGER.getName(getFusionId()));
    }
}
