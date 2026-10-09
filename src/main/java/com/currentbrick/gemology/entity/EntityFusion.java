package com.currentbrick.gemology.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.FusionUIContainer;
import com.currentbrick.gemology.container.GemUIContainer;
import com.currentbrick.gemology.entity.gem.FusionVisualVariant;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.currentbrick.gemology.entity.gem.GemStats;
import com.currentbrick.gemology.entity.gem.palette.GemPalette;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteGenerator;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteLoader;
import com.currentbrick.gemology.init.ModEntities;
import com.currentbrick.gemology.item.FusionItem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IMenuProviderExtension;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EntityFusion extends Monster implements GeoAnimatable, Container, MenuProvider, IMenuProviderExtension, ContainerListener {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private CompoundTag firstGemData;
    private CompoundTag secondGemData;

    private static final EntityDataAccessor<String> FUSION_GEM_1 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FUSION_GEM_2 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FUSION_INSTANCE_1 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> FUSION_INSTANCE_2 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> FUSION_VARIANT_1 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FUSION_VARIANT_2 = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> FUSION_ID = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Float> FUSION_HEALTH = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> FUSION_STRENGTH = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> FUSION_SPEED = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> FUSION_ABILITIES = SynchedEntityData.defineId(EntityFusion.class, EntityDataSerializers.STRING);

    private static final int INVENTORY_SIZE = 32;

    private final NonNullList<ItemStack> fusionInventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);


    private GemStats fusionStats;
    private GemDimensions fusionDimensions;

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

        builder.define(FUSION_INSTANCE_1, "");
        builder.define(FUSION_INSTANCE_2, "");

        builder.define(FUSION_VARIANT_1, -1);
        builder.define(FUSION_VARIANT_2, -1);

        builder.define(FUSION_HEALTH, 0F);
        builder.define(FUSION_STRENGTH, 0F);
        builder.define(FUSION_SPEED, 0F);

        builder.define(FUSION_ABILITIES, "");
    }

    public void setComponents(CompoundTag first, CompoundTag second) {
        this.firstGemData = first;
        this.secondGemData = second;

        Identifier gem1 = getGemId(first);
        Identifier gem2 = getGemId(second);

        entityData.set(FUSION_GEM_1, gem1 != null ? gem1.toString() : "");
        entityData.set(FUSION_GEM_2, gem2 != null ? gem2.toString() : "");
        entityData.set(FUSION_INSTANCE_1, first.getString("InstanceId").orElse(""));
        entityData.set(FUSION_INSTANCE_2, second.getString("InstanceId").orElse(""));
        entityData.set(FUSION_VARIANT_1, first.getInt("Variant").orElse(-1));
        entityData.set(FUSION_VARIANT_2, second.getInt("Variant").orElse(-1));


        System.out.println(
                "FUSION SET COMPONENTS: " +
                        entityData.get(FUSION_GEM_1) + " | " +
                        entityData.get(FUSION_INSTANCE_1) + " | " +
                        entityData.get(FUSION_VARIANT_1)
        );
    }

    public UUID getGem1InstanceId() {
        String id = entityData.get(FUSION_INSTANCE_1);

        if (id.isEmpty()) {
            return null;
        }

        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public UUID getGem2InstanceId() {
        String id = entityData.get(FUSION_INSTANCE_2);

        if (id.isEmpty()) {
            return null;
        }

        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public int getGem1Variant() {
        return entityData.get(FUSION_VARIANT_1);
    }

    public int getGem2Variant() {
        return entityData.get(FUSION_VARIANT_2);
    }


    public static Identifier getGemId(CompoundTag data) {
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

        entityData.set(FUSION_HEALTH, fusionStats.getHealth());
        entityData.set(FUSION_STRENGTH, fusionStats.getStrength());
        entityData.set(FUSION_SPEED, fusionStats.getSpeed());
    }

    public List<Identifier> getFusionAbilities() {
        String serialized = entityData.get(FUSION_ABILITIES);

        if (serialized.isEmpty()) {
            return List.of();
        }

        return Arrays.stream(serialized.split(","))
                .map(Identifier::tryParse)
                .filter(Objects::nonNull)
                .toList();
    }

    public void setFusionAbilities(List<Identifier> fusionAbilities) {
        String serialized = fusionAbilities.stream()
                .map(Identifier::toString)
                .collect(Collectors.joining(","));

        entityData.set(FUSION_ABILITIES, serialized);
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

        ContainerHelper.saveAllItems(output, fusionInventory);
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
        entityData.set(FUSION_ID, input.getLongOr("FusionID", 0L));

        if (firstGemData != null) {
            entityData.set(
                    FUSION_INSTANCE_1,
                    firstGemData.getString("InstanceId").orElse("")
            );
            entityData.set(
                    FUSION_VARIANT_1,
                    firstGemData.getInt("Variant").orElse(-1)
            );
        }

        if (secondGemData != null) {
            entityData.set(
                    FUSION_INSTANCE_2,
                    secondGemData.getString("InstanceId").orElse("")
            );
            entityData.set(
                    FUSION_VARIANT_2,
                    secondGemData.getInt("Variant").orElse(-1)
            );
        }

        ContainerHelper.loadAllItems(input, fusionInventory);
    }

    public void applyStats() {
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(fusionStats.getHealth());
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(fusionStats.getSpeed());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(fusionStats.getStrength());
    }

    public float getFusionHealth() {
        return entityData.get(FUSION_HEALTH);
    }

    public float getFusionStrength() {
        return entityData.get(FUSION_STRENGTH);
    }

    public float getFusionSpeed() {
        return entityData.get(FUSION_SPEED);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(test -> {
            if (test.isMoving())
                return test.setAndContinue(DefaultAnimations.WALK);

            return test.setAndContinue(DefaultAnimations.IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        System.out.println("interact");
        if (level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (firstGemData == null || secondGemData == null) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(hand);
        System.out.println("check fusion item");
        if ((stack.getItem() instanceof FusionItem)) {
            unfuse(player);

            return InteractionResult.SUCCESS;
        } else {
            System.out.println("open menu");
            player.openMenu(this, buf -> buf.writeInt(this.getId()));
            return InteractionResult.SUCCESS;
        }
    }

    public void unfuse(Player player) {
        double offset = 1;

        Vec3 direction = player.getLookAngle();
        direction = new Vec3(direction.x, 0, direction.z).normalize();

        // Handle the case where the player is looking straight up or down.
        if (direction.lengthSqr() < 1.0E-6) {
            direction = new Vec3(0, 0, 1);
        }

        Vec3 side = new Vec3(-direction.z, 0, direction.x).normalize();

        spawnGem(firstGemData, -side.x * offset, 0, -side.z * offset, 0);
        spawnGem(secondGemData, side.x * offset, 0, side.z * offset, 16);

        discard();
    }

    private void spawnGem(CompoundTag gemData, double offsetX, double offsetY, double offsetZ, int inventoryOffset) {
        EntityGem gem = ModEntities.GEM.get().create(level(), EntitySpawnReason.SPAWN_ITEM_USE);

        if (gem == null) {
            return;
        }

        gem.applyGemData(gemData);

        for (int i = 0; i < gem.getContainerSize(); i++) {
            gem.setItem(i, getItem(inventoryOffset + i).copy());
        }

        gem.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);

        level().addFreshEntity(gem);
    }

    public void setFusionId(Long id) {
        entityData.set(FUSION_ID, id);
    }

    public Long getFusionId() {
        return entityData.get(FUSION_ID);
    }

    private int getVisualVariant(int count, String category) {
        if (count <= 1) {
            return 0;
        }

        long seed = getFusionId() ^ category.hashCode();

        RandomSource random = RandomSource.create(seed);

        return random.nextInt(count);
    }

    public FusionVisualVariant getVisualVariant() {
        return new FusionVisualVariant(
                getVisualVariant(1, "skin"),
                getVisualVariant(1, "hair"),
                getVisualVariant(1, "outfit"),
                getVisualVariant(1, "insignia"),
                getVisualVariant(8, "eyes")
        );
    }

    public int getCombinedPaletteColour(GemPaletteGenerator.PaletteType type) {
        int colour1 = getComponentPaletteColour(
                getGem1ID(),
                getGem1InstanceId(),
                getGem1Variant() < 0 ? 0 : getGem1Variant(),
                type
        );

        int colour2 = getComponentPaletteColour(
                getGem2ID(),
                getGem2InstanceId(),
                getGem2Variant() < 0 ? 0 : getGem2Variant(),
                type
        );

        return GemPaletteGenerator.combineColours(colour1, colour2);
    }

    private int getComponentPaletteColour(
            Identifier gemId,
            UUID instanceId,
            int variant,
            GemPaletteGenerator.PaletteType type
    ) {
        if (gemId == null || instanceId == null) {
            System.out.printf(
                    "%s — Missing data: gemId=%s, instanceId=%s%n",
                    type, gemId, instanceId
            );
            return 0xFFFFFFFF;
        }

        Identifier paletteTexture = Identifier.fromNamespaceAndPath(
                gemId.getNamespace(),
                "textures/entity/" + gemId.getPath()
                        + "/palettes/" + type.name().toLowerCase() + "_palette.png"
        );

        try {
            GemPalette palette = GemPaletteLoader.load(
                    Minecraft.getInstance().getResourceManager(),
                    paletteTexture
            );

            if (variant < 0 || variant >= palette.getRowCount()) {
                System.out.printf(
                        "%s — INVALID VARIANT: %d (valid range: 0–%d)%n",
                        type, variant, palette.getRowCount() - 1
                );
            }

            int colour = GemPaletteGenerator.generate(
                    palette, variant, instanceId, type
            );

            return colour;
        } catch (IOException e) {
            Gemology.LOGGER.warn(
                    "Failed to load palette {} for gem {}",
                    paletteTexture, gemId, e
            );
            return 0xFFFFFFFF;
        }
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return EntityDimensions.scalable(
                entityData.get(FUSION_WIDTH),
                entityData.get(FUSION_HEIGHT)
        );
    }

    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : fusionInventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return fusionInventory.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = ContainerHelper.removeItem(fusionInventory, index, count);

        if (!stack.isEmpty()) {
            setChanged();
        }

        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = fusionInventory.get(index);
        fusionInventory.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        fusionInventory.set(index, stack);

        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return isAlive() && player.distanceToSqr(this) <= 64.0;
    }

    @Override
    public void clearContent() {
        fusionInventory.clear();
    }

    @Override
    public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {

    }

    @Override
    public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int i1) {

    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FusionUIContainer(id, inventory, this);
    }

    public String getGemName() {
        String gemName = getGem1ID().getPath();

        String[] words = gemName.split("_");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return result.toString().trim();
    }

    private Identifier getFusionTypeId(Identifier gemId) {
        if (gemId == null) {
            return null;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            return gemId;
        }

        return definition.getFusionTypeId();
    }

    @Override
    public Component getDisplayName() {
        if (getFusionTypeId(getGem1ID()).equals(getFusionTypeId(getGem1ID()))) return Component.literal(getGemName());
        return Component.literal(Gemology.FUSION_NAME_MANAGER.getName(getFusionId()));
    }
}
