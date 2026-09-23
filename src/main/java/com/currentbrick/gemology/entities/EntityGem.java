package com.currentbrick.gemology.entities;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entities.ai.GemFollowOwnerGoal;
import com.currentbrick.gemology.entities.ai.GemWanderGoal;
import com.currentbrick.gemology.entities.ai.MovementMode;
import com.currentbrick.gemology.entities.gem.GemDefinition;
import com.currentbrick.gemology.entities.gem.GemDimensions;
import com.currentbrick.gemology.entities.gem.GemStats;
import com.currentbrick.gemology.entities.gem.abilities.Ability;
import com.currentbrick.gemology.entities.gem.abilities.AbilityDefinition;
import com.currentbrick.gemology.entities.gem.abilities.AbilityTypeRegistry;
import com.currentbrick.gemology.init.ModEntities;
import com.currentbrick.gemology.init.ModItems;
import com.currentbrick.gemology.items.FusionItem;
import com.currentbrick.gemology.items.ItemGem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class EntityGem extends Monster implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final int ABILITY_CHECK_INTERVAL = 10;

    private MovementMode movementMode = MovementMode.WANDER;

    private static final EntityDataAccessor<String> GEM_ID = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.STRING);

    public EntityGem(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new GemWanderGoal(this, 1.0));
        goalSelector.addGoal(5, new GemFollowOwnerGoal(this, 1.0, 2, 6));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    public void setGemId(Identifier gemId) {
        this.entityData.set(GEM_ID, gemId.toString());
    }

    public Identifier getGemId() {
        String value = this.entityData.get(GEM_ID);

        if (value.isEmpty()) {
            return null;
        }

        return Identifier.parse(value);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide() && tickCount % ABILITY_CHECK_INTERVAL == 0) {
            executePassiveAbilities();
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key.equals(GEM_ID)) {
            refreshDimensions();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(GEM_ID, "");
        builder.define(OWNER_UUID, "");
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        input.getString("GemType").ifPresent(value -> {
            setGemId(Identifier.parse(value));
        });

        input.getString("Owner").ifPresent(value -> {
            setOwnerUUID(UUID.fromString(value));
        });

        input.getString("MovementMode").ifPresent(value -> {
            movementMode = MovementMode.valueOf(value);
        });

        applyGemStats();
        refreshDimensions();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        Identifier gemId = getGemId();

        if (gemId != null) {
            output.putString("GemType", gemId.toString());
        }

        UUID ownerUUID = getOwnerUUID();

        if (ownerUUID != null) {
            output.putString("Owner", ownerUUID.toString());
        }

        output.putString("MovementMode", movementMode.name());
    }

    public CompoundTag createGemData() {
        CompoundTag tag = new CompoundTag();

        Identifier gemId = getGemId();

        if (gemId != null) {
            tag.putString("GemType", gemId.toString());
        }

        UUID ownerUUID = getOwnerUUID();

        if (ownerUUID != null) {
            tag.putString("Owner", ownerUUID.toString());
        }

        return tag;
    }

    public void applyGemData(CompoundTag tag) {

        tag.getString("GemType").ifPresent(value -> {
            setGemId(Identifier.parse(value));
        });

        tag.getString("Owner").ifPresent(value -> {
            setOwnerUUID(UUID.fromString(value));
        });

        applyGemStats();
        refreshDimensions();
    }

    public MovementMode getMovementMode() {
        return movementMode;
    }

    public void setMovementMode(MovementMode movementMode) {
        this.movementMode = movementMode;
    }

    public void setOwnerUUID(UUID uuid) {
        this.entityData.set(OWNER_UUID, uuid == null ? "" : uuid.toString());
    }

    public UUID getOwnerUUID() {
        String value = this.entityData.get(OWNER_UUID);

        if (value.isEmpty()) {
            return null;
        }

        return UUID.fromString(value);
    }

    public void setOwner(Player player) {
        setOwnerUUID(player.getUUID());
    }

    public Player getOwner() {
        UUID uuid = getOwnerUUID();

        if (uuid == null) {
            return null;
        }

        return level().getPlayerByUUID(uuid);
    }

    public boolean hasOwner() {
        return !this.entityData.get(OWNER_UUID).isEmpty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    private void applyGemStats() {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            return;
        }

        GemStats stats = definition.getStats();

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(stats.getHealth());
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(stats.getSpeed());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(stats.getStrength());
    }


    private void applyGemDimensions() {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            return;
        }

        GemDimensions dimensions = definition.getDimensions();

    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        Identifier gemId = getGemId();

        if (gemId != null) {
            GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

            if (definition != null) {
                GemDimensions dimensions = definition.getDimensions();

                return EntityDimensions.scalable(dimensions.getWidth(), dimensions.getHeight());
            }
        }

        return super.getDefaultDimensions(pose);
    }

    private GemDefinition getGemDefinition() {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return null;
        }

        return Gemology.GEM_DEFINITION_MANAGER.get(gemId);
    }

    private void executePassiveAbilities() {
        GemDefinition definition = getGemDefinition();

        if (definition == null) {
            return;
        }

        for (Identifier abilityId : definition.getAbilities()) {
            AbilityDefinition abilityDefinition = Gemology.ABILITY_MANAGER.get(abilityId);

            if (abilityDefinition == null) {
                continue;
            }

            if (!Identifier.fromNamespaceAndPath(Gemology.MODID, "passive").equals(abilityDefinition.getTrigger())) {
                continue;
            }

            Ability ability = AbilityTypeRegistry.create(abilityDefinition.getType());

            if (ability == null) {
                continue;
            }

            ability.execute(this, abilityDefinition);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {

        if (level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (hasOwner() && player.getUUID().equals(getOwnerUUID())) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof FusionItem) {
                CustomData customData = stack.get(DataComponents.CUSTOM_DATA);

                if (customData == null) {
                    CompoundTag tag = new CompoundTag();
                    CompoundTag firstGemData = createGemData();

                    tag.put("FirstGemData", firstGemData);
                    tag.putString("FirstGemUUID", getUUID().toString());

                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                    player.sendSystemMessage(Component.literal(getGemName() + " selected"));

                    return InteractionResult.SUCCESS;
                }

                CompoundTag fusionData = customData.copyTag();

                fusionData.getCompound("FirstGemData").ifPresent(firstGemData -> {

                    EntityGem firstGem = null;

                    if (fusionData.getString("FirstGemUUID").isPresent()) {
                        UUID firstGemUUID = UUID.fromString(fusionData.getString("FirstGemUUID").get());
                        Entity entity = level().getEntity(firstGemUUID);

                        if (entity instanceof EntityGem) {
                            firstGem = (EntityGem) entity;
                        }
                    }

                    CompoundTag secondGemData = createGemData();

                    EntityFusion fusion = ModEntities.FUSION.get().create(level(), EntitySpawnReason.SPAWN_ITEM_USE
                    );

                    if (fusion == null) {
                        return;
                    }

                    fusion.setComponents(firstGemData, secondGemData);

                    fusion.setPos(getX(), getY(), getZ());

                    level().addFreshEntity(fusion);

                    if (firstGem != null) {
                        firstGem.discard();
                    }

                    discard();

                    stack.remove(DataComponents.CUSTOM_DATA);

                    player.sendSystemMessage(Component.literal("Fused " + Identifier.parse(firstGemData.getString("GemType").orElse("unknown")).getPath().substring(0, 1).toUpperCase() + Identifier.parse(firstGemData.getString("GemType").orElse("unknown")).getPath().substring(1) + " + " + getGemName()));
                });

                return InteractionResult.SUCCESS;
            }
        }

        if (hasOwner() && player.getUUID().equals(getOwnerUUID()) && player.isShiftKeyDown()) {
            setMovementMode(getMovementMode().next());
            player.sendSystemMessage(Component.literal("Set "  + getGemName() + " to "+ getMovementMode().name().toLowerCase().replace("_", " ")));
            return InteractionResult.SUCCESS;
        }

        if (hasOwner()) {
            return InteractionResult.PASS;
        }

        setOwner(player);

        player.sendSystemMessage(Component.literal("Claimed "+getGemName()));

        Gemology.LOGGER.info("Gem claimed by {}", player.getName().getString());

        return InteractionResult.SUCCESS;
    }

    public String getGemName() {
        String gemName = getGemId().getPath();

        return gemName.substring(0, 1).toUpperCase() + gemName.substring(1);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);

        ItemStack stack = createGemItem();

        if (stack != null) {
            spawnAtLocation(level, stack);
        }
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    private ItemStack createGemItem() {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return ItemStack.EMPTY;
        }

        ItemGem item = ModItems.getGemItem(gemId);

        if (item == null) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = new ItemStack(item);
        UUID ownerUUID = getOwnerUUID();

        if (ownerUUID != null) {
            CompoundTag tag = new CompoundTag();
            tag.putString("Owner", ownerUUID.toString());
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        return stack;
    }
}
