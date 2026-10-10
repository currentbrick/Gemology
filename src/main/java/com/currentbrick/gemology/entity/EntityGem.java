package com.currentbrick.gemology.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.GemUIContainer;
import com.currentbrick.gemology.entity.gem.ai.GemFollowOwnerGoal;
import com.currentbrick.gemology.entity.gem.ai.GemWanderGoal;
import com.currentbrick.gemology.entity.gem.ai.MovementMode;
import com.currentbrick.gemology.entity.fusion.FusionGenerator;
import com.currentbrick.gemology.entity.gem.*;
import com.currentbrick.gemology.entity.gem.abilities.Ability;
import com.currentbrick.gemology.entity.gem.abilities.AbilityDefinition;
import com.currentbrick.gemology.entity.gem.abilities.AbilityTrigger;
import com.currentbrick.gemology.entity.gem.abilities.AbilityTypeRegistry;
import com.currentbrick.gemology.entity.gem.ai.GemMeleeAttackGoal;
import com.currentbrick.gemology.entity.gem.ai.GemRangedAttackGoal;
import com.currentbrick.gemology.entity.gem.ai.GemTargetGoal;
import com.currentbrick.gemology.entity.gem.palette.GemPalette;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteGenerator;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteLoader;
import com.currentbrick.gemology.init.ModEntities;
import com.currentbrick.gemology.init.ModItems;
import com.currentbrick.gemology.init.ModSounds;
import com.currentbrick.gemology.item.FusionItem;
import com.currentbrick.gemology.item.ItemGem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IMenuProviderExtension;

import java.io.IOException;
import java.util.*;

public class EntityGem extends Monster implements GeoAnimatable, Container, MenuProvider, IMenuProviderExtension, ContainerListener {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final int ABILITY_CHECK_INTERVAL = 10;

    private MovementMode movementMode = MovementMode.WANDER;
    private GemInstanceData instanceData;
    private int pendingVariant = -1;
    private int secondaryAttackCooldown = 0;
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.INT);

    private static final int INVENTORY_SIZE = 16;

    private final NonNullList<ItemStack> gemInventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

    private UUID fusionTargetUUID;
    private CompoundTag pendingFusionGemData;
    private boolean fusionPending = false;
    private boolean completingFusion = false;

    private final Map<GemPaletteGenerator.PaletteType, GemPalette> palettes = new EnumMap<>(GemPaletteGenerator.PaletteType.class);

    private static final EntityDataAccessor<String> GEM_ID = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> INSTANCE_ID = SynchedEntityData.defineId(EntityGem.class, EntityDataSerializers.STRING);

    public EntityGem(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    private void ensureInstanceData() {
        if (instanceData != null) {
            return;
        }

        System.out.println("WARNING: GENERATING/RESTORING INSTANCE DATA");

        Identifier gemId = getGemId();

        if (gemId == null) {
            return;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            return;
        }

        String syncedId = this.entityData.get(INSTANCE_ID);

        if (!syncedId.isEmpty()) {
            UUID instanceId = UUID.fromString(syncedId);

            int variant = this.entityData.get(VARIANT);

            if (variant == -1) {
                variant = generateRandomVariant();
            }

            instanceData = new GemInstanceData(instanceId, 1.0F, variant);

            System.out.println("RESTORED FROM SYNC: " + instanceId);

            return;
        }

        int variant = pendingVariant;

        if (variant == -1) {
            variant = generateRandomVariant();
        }

        UUID newId = UUID.randomUUID();

        System.out.println("!!! GENERATED NEW UUID: " + newId);

        setInstanceData(new GemInstanceData(newId, 1.0F, variant));
    }

    public void setPendingVariant(int variant) {
        this.pendingVariant = variant;
    }

    private static int randomVariant(int count, RandomSource random) {
        if (count <= 1) {
            return 0;
        }

        return random.nextInt(count);
    }


    public GemInstanceData getInstanceData() {
        ensureInstanceData();
        return instanceData;
    }

    public void setInstanceData(GemInstanceData instanceData) {
        this.instanceData = instanceData;

        if (instanceData != null) {
            this.pendingVariant = instanceData.getVariant();

            this.entityData.set(
                    INSTANCE_ID,
                    instanceData.getInstanceId().toString()
            );

            this.entityData.set(
                    VARIANT,
                    instanceData.getVariant()
            );
        } else {
            this.pendingVariant = -1;

            this.entityData.set(INSTANCE_ID, "");
            this.entityData.set(VARIANT, -1);
        }
    }

    private int generateRandomVariant() {
        return generateRandomVariant(getGemId(), random);
    }

    public void loadPalettes(ResourceManager resourceManager) {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return;
        }

        palettes.clear();

        String gemPath = gemId.getPath();

        for (GemPaletteGenerator.PaletteType type : GemPaletteGenerator.PaletteType.values()) {

            String paletteName = switch (type) {
                case SKIN -> "skin_palette.png";
                case HAIR -> "hair_palette.png";
                case GEM -> "gem_palette.png";
                case OUTFIT -> "outfit_palette.png";
                case INSIGNIA -> "insignia_palette.png";
                case MARKINGS -> "marking_palette.png";
                case WINGS -> "wing_palette.png";
            };

            Identifier paletteId = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/" + gemPath + "/palettes/" + paletteName);

            try {
                GemPalette palette = GemPaletteLoader.load(resourceManager, paletteId);
                palettes.put(type, palette);

            } catch (IOException e) {
                if (type == GemPaletteGenerator.PaletteType.INSIGNIA || type == GemPaletteGenerator.PaletteType.MARKINGS || type == GemPaletteGenerator.PaletteType.WINGS) {
                    continue;
                }

                System.err.println("[Gemology] Failed to load required palette: " + paletteId);
                e.printStackTrace();
            }
        }
    }

    public int getPaletteColour(GemPaletteGenerator.PaletteType type) {
        ensureInstanceData();

        if (instanceData == null) {
            return 0xFFFFFFFF;
        }

        GemPalette palette = palettes.get(type);

        if (palette == null) {
            return 0xFFFFFFFF;
        }

        int variantId = instanceData.getVariant();


        if (variantId == -1) {
            variantId = 0;
        }

        return GemPaletteGenerator.generate(
                palette,
                variantId,
                instanceData.getInstanceId(),
                type
        );
    }

    public boolean arePalettesLoaded() {
        return !palettes.isEmpty();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new GemWanderGoal(this, 1.0));
        goalSelector.addGoal(5, new GemFollowOwnerGoal(this, 1.0, 2, 6));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        targetSelector.addGoal(1, new GemTargetGoal(this));
        goalSelector.addGoal(2, new GemMeleeAttackGoal(this, 1.0D, true));
        goalSelector.addGoal(3, new GemRangedAttackGoal(this, 1.0D));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public boolean canTarget(LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (target == this) {
            return false;
        }

        if (target instanceof EntityGem) {
            return false;
        }

        if (target instanceof EntityFusion) {
            return false;
        }

        return target instanceof Monster;
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

        if (!level().isClientSide()) {
            if (fusionPending) {
                handleFusionApproach();
            }

            if (tickCount % ABILITY_CHECK_INTERVAL == 0) {
                executePassiveAbilities();
            }
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
        builder.define(INSTANCE_ID, "");
        builder.define(VARIANT, 0);
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

        input.getString("InstanceId").ifPresent(value -> {

            UUID instanceId = UUID.fromString(value);

            float quality = input.getFloatOr("Quality", 1.0F);
            int variant = input.getIntOr("Variant", -1);
            setInstanceData(new GemInstanceData(
                    instanceId,
                    quality,
                    variant
            ));

            System.out.println("LOADED INSTANCE UUID: " + instanceData.getInstanceId());
        });

        ContainerHelper.loadAllItems(input, gemInventory);

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

        GemInstanceData instance = getInstanceData();

        output.putString("InstanceId", instance.getInstanceId().toString());
        output.putFloat("Quality", instance.getQuality());
        output.putInt("Variant", instance.getVariant());

        ContainerHelper.saveAllItems(output, gemInventory);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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

        GemInstanceData instance = getInstanceData();

        tag.putString("InstanceId", instance.getInstanceId().toString());
        tag.putFloat("Quality", instance.getQuality());
        tag.putInt("Variant", instance.getVariant());

        System.out.println("INSTANCE: " + getInstanceData().getInstanceId());

        return tag;
    }

    public void applyGemData(CompoundTag tag) {
        tag.getString("GemType").ifPresent(value ->
                setGemId(Identifier.parse(value))
        );

        tag.getString("Owner").ifPresent(value ->
                setOwnerUUID(UUID.fromString(value))
        );

        tag.getString("InstanceId").ifPresent(value -> {
            UUID instanceId = UUID.fromString(value);
            float quality = tag.getFloat("Quality").orElse(1.0F);
            int variant = tag.getInt("Variant").orElse(-1);

            System.out.println(
                    "APPLY GEM DATA | instance="
                            + instanceId
                            + " | variant="
                            + variant
            );

            setInstanceData(new GemInstanceData(instanceId, quality, variant));
        });

        if (instanceData == null) {
            int variant = tag.getInt("Variant").orElse(-1);

            if (variant != -1) {
                pendingVariant = variant;
            }
        }

        applyGemStats();
        refreshDimensions();
    }

    private int getVisualVariant(int count, String category) {
        if (count <= 1 || instanceData == null) {
            return 0;
        }

        UUID uuid = instanceData.getInstanceId();

        long seed = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits() ^ category.hashCode();

        RandomSource random = RandomSource.create(seed);

        return random.nextInt(count);
    }

    public static int getVisualVariant(UUID instanceId, int count, String category) {
        if (instanceId == null || count <= 1) return 0;

        long seed = instanceId.getMostSignificantBits() ^ instanceId.getLeastSignificantBits() ^ category.hashCode();

        RandomSource random = RandomSource.create(seed);

        return random.nextInt(count);
    }

    public GemVisualVariant getVisualVariant() {
        ensureInstanceData();

        if (instanceData == null) {
            return new GemVisualVariant(0, 0, 0, 0, 0, 0, 0);
        }

        GemDefinition definition = getGemDefinition();

        if (definition == null) {
            return new GemVisualVariant(0, 0, 0, 0, 0, 0, 0);
        }

        int skin = getVisualVariant(definition.getSkinVariants(), "skin");
        int hair = getVisualVariant(definition.getHairVariants(), "hair");
        int gem = getVisualVariant(definition.getGemVariants(), "gem");
        int outfit = getVisualVariant(definition.getOutfitVariants(), "outfit");
        int insignia = getVisualVariant(definition.getInsigniaVariants(), "insignia");

        int visor = -1;
        int wing = -1;

        if (definition.getWingVariants() > 0) {
            wing = getVisualVariant(definition.getWingVariants(), "wing");
        }

        if (definition.getVisorVariants() > 0 && getVisualVariant(100, "visor_chance") < 20) {
            visor = getVisualVariant(definition.getVisorVariants(), "visor");
        }

        int marking = -1;

        GemVariant gemVariant = getGemVariant();

        if (gemVariant != null && gemVariant.getMarkingVariants() > 0) {
            marking = getVisualVariant(gemVariant.getMarkingVariants(), "markings");
        }

        return new GemVisualVariant(skin, hair, gem, outfit, marking, visor, wing);
    }

    public GemVariant getGemVariant() {
        ensureInstanceData();

        if (instanceData == null) {
            return null;
        }

        GemDefinition definition = getGemDefinition();

        if (definition == null) {
            return null;
        }

        int variantId = instanceData.getVariant();

        return definition.getVariants().stream().filter(variant -> variant.getId() == variantId).findFirst().orElse(null);
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
        /*controllers.add(new AnimationController<>("movement", 5, state -> {
                            if (state.isMoving()) {
                                return state.setAndContinue(RawAnimation.begin().thenLoop("walk"));
                            }
                            return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
                        }
                )
        );*/
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

    public void startFusionApproach(EntityGem target, CompoundTag targetGemData) {
        if (target == null) {
            return;
        }

        this.fusionTargetUUID = target.getUUID();
        this.pendingFusionGemData = targetGemData.copy();
        this.fusionPending = true;
    }

    private void handleFusionApproach() {
        if (fusionTargetUUID == null) {
            fusionPending = false;
            return;
        }

        Entity target = level().getEntity(fusionTargetUUID);

        if (!(target instanceof EntityGem fusionTarget) || !fusionTarget.isAlive()) {
            fusionPending = false;
            fusionTargetUUID = null;
            pendingFusionGemData = null;
            return;
        }

        double distance = distanceTo(fusionTarget);

        if (distance <= 0.5D) {
            completeFusion(fusionTarget);
            return;
        }

        getNavigation().moveTo(fusionTarget, 1.5D);
    }

    private void completeFusion(EntityGem target) {
        if (completingFusion || target.completingFusion) {
            return;
        }

        completingFusion = true;
        target.completingFusion = true;

        CompoundTag firstGemData = createGemData();

        CompoundTag secondGemData = target.createGemData();

        GemInstanceData firstInstance = FusionGenerator.getInstanceData(firstGemData);

        GemInstanceData secondInstance = FusionGenerator.getInstanceData(secondGemData);

        long seed = FusionGenerator.getFusionSeed(firstInstance, secondInstance);
        System.out.println("FUSION SEED: " + seed);

        RandomSource random = FusionGenerator.createRandom(seed);

        GemDefinition firstDefinition = Gemology.GEM_DEFINITION_MANAGER.get(Identifier.parse(firstGemData.getString("GemType").orElseThrow()));
        GemDefinition secondDefinition = Gemology.GEM_DEFINITION_MANAGER.get(Identifier.parse(secondGemData.getString("GemType").orElseThrow()));

        GemStats fusionStats = FusionGenerator.generateStats(firstDefinition, firstInstance, secondDefinition, secondInstance, random);
        List<Identifier> fusionAbilities = FusionGenerator.generateAbilities(firstDefinition, secondDefinition, random);
        GemDimensions fusionDimensions = FusionGenerator.generateDimensions(firstDefinition, firstInstance, secondDefinition, secondInstance, random);

        System.out.println("FUSION STATS: " + fusionStats.getHealth() + ", " + fusionStats.getStrength() + ", " + fusionStats.getSpeed());
        System.out.println("FUSION DIMS: " + fusionDimensions.getHeight() + ", " + fusionDimensions.getWidth());

        EntityFusion fusion = ModEntities.FUSION.get().create(level(), EntitySpawnReason.SPAWN_ITEM_USE);

        if (fusion == null) {
            return;
        }

        this.copyInventoryTo(fusion, 0);

        target.copyInventoryTo(fusion, 16);

        fusion.setComponents(firstGemData, secondGemData);
        fusion.setFusionId(seed);
        fusion.setFusionStats(fusionStats);
        fusion.setFusionAbilities(fusionAbilities);
        fusion.setFusionDimensions(fusionDimensions);

        fusion.setPos(getX(), getY(), getZ());

        level().addFreshEntity(fusion);

        this.clearInventory();
        target.clearInventory();

        discard();
        target.discard();

        String secondGemType = secondGemData.getString("GemType").orElse("unknown");
        String secondGemName = Identifier.parse(secondGemType).getPath();
        secondGemName = secondGemName.substring(0, 1).toUpperCase() + secondGemName.substring(1);

        getOwner().sendSystemMessage(Component.literal(
                "Fused " + getGemName() + " + " + secondGemName
        ));
    }

    public boolean isFusionPending() {
        return fusionPending;
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
        GemInstanceData instance = getInstanceData();

        if (instance == null) {
            return;
        }

        UUID instanceId = instance.getInstanceId();
        float quality = instance.getQuality();

        float health = GemStatCalculator.calculateStat(stats.getHealth(), quality, instanceId, "health");

        float strength = GemStatCalculator.calculateStat(stats.getStrength(), quality, instanceId, "strength");

        float speed = GemStatCalculator.calculateStat(stats.getSpeed(), quality, instanceId, "speed");

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(speed);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(strength);

        setHealth(Math.min(getHealth(), getMaxHealth()));
    }


    public double getHealthStat() {
        return getAttribute(Attributes.MAX_HEALTH).getBaseValue();
    }

    public double getSpeedStat() {
        return getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
    }

    public double getStrengthStat() {
        return getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
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

            ability.execute(this, abilityDefinition, null);
        }
    }

    private void executeAbilities(AbilityTrigger trigger, LivingEntity target) {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            return;
        }

        for (Identifier abilityId : definition.getAbilities()) {

            AbilityDefinition abilityDefinition = Gemology.ABILITY_MANAGER.get(abilityId);

            if (abilityDefinition == null) {
                continue;
            }

            if (!abilityDefinition.getTrigger().equals(trigger.getId())) {
                continue;
            }

            Ability ability = AbilityTypeRegistry.create(abilityDefinition.getType());

            if (ability == null) {
                System.err.println("Unknown ability type: " + abilityDefinition.getType());
                continue;
            }

            ability.execute(this, abilityDefinition, target);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean successful = super.doHurtTarget(level, target);

        if (successful && target instanceof LivingEntity livingTarget) {
            executeAbilities(AbilityTrigger.ATTACK, livingTarget);
        }

        return successful;
    }

    public void performSecondaryAttack(LivingEntity target) {

        if (!(level() instanceof ServerLevel)) {
            return;
        }

        if (secondaryAttackCooldown > 0) {
            return;
        }

        if (target == null || !target.isAlive()) {
            return;
        }

        executeAbilities(
                AbilityTrigger.SECONDARY_ATTACK,
                target
        );

        secondaryAttackCooldown = 60;
    }

    private boolean hasAbilityForTrigger(AbilityTrigger trigger) {
        Identifier gemId = getGemId();

        if (gemId == null) {
            return false;
        }

        GemDefinition gemDefinition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (gemDefinition == null) {
            return false;
        }

        for (Identifier abilityId : gemDefinition.getAbilities()) {

            AbilityDefinition ability = Gemology.ABILITY_MANAGER.get(abilityId);

            if (ability == null) {
                continue;
            }

            if (ability.getTrigger().equals(trigger.getId())) {
                return true;
            }
        }

        return false;
    }

    public boolean hasMeleeAbility() {
        return hasAbilityForTrigger(AbilityTrigger.ATTACK);
    }

    public boolean hasRangedAbility() {
        return hasAbilityForTrigger(
                AbilityTrigger.SECONDARY_ATTACK
        );
    }

    public boolean hasCombatAbility() {
        return hasMeleeAbility() || hasRangedAbility();
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

                    player.sendSystemMessage(Component.literal(getGemName() + " selected"));
                    this.playSound(getInstrument(), this.getSoundVolume(), (interactPitch()));
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

                    if (!getUUID().toString().equals(fusionData.getString("FirstGemUUID").orElse(""))) {

                        if (firstGem != null) {
                            firstGem.startFusionApproach(this, secondGemData);

                            startFusionApproach(firstGem, firstGemData);
                        }

                        stack.remove(DataComponents.CUSTOM_DATA);

                        stack.hurtAndBreak(1, player, hand);
                    }
                });

                this.playSound(getInstrument(), this.getSoundVolume(), (interactPitch()));
                return InteractionResult.SUCCESS;
            }
        }

        if (hasOwner() && player.getUUID().equals(getOwnerUUID()) && player.isShiftKeyDown()) {
            setMovementMode(getMovementMode().next());
            player.sendSystemMessage(Component.literal("Set "  + getGemName() + " to "+ getMovementMode().name().toLowerCase().replace("_", " ")));
            this.playSound(getInstrument(), this.getSoundVolume(), (interactPitch()));
            return InteractionResult.SUCCESS;
        }

        if (hasOwner() && player.getUUID().equals(getOwnerUUID())) {
            this.playSound(getInstrument(), this.getSoundVolume(), (interactPitch()));
            player.openMenu(this);
        }

        if (hasOwner()) {
            return InteractionResult.PASS;
        }

        setOwner(player);

        player.sendSystemMessage(Component.literal("Claimed "+getGemName()));

        Gemology.LOGGER.info("Gem claimed by {}", player.getName().getString());
        this.playSound(getInstrument(), this.getSoundVolume(), (interactPitch()));
        return InteractionResult.SUCCESS;
    }

    public String getGemName() {
        String gemName = getGemId().getPath();

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
        GemInstanceData instance = getInstanceData();

        return createGemItem(getGemId(), instance.getInstanceId(), instance.getQuality(), instance.getVariant());
    }

    public static ItemStack createGemItem(Identifier gemId, UUID instanceId, float quality, int variant) {
        if (gemId == null) {
            return ItemStack.EMPTY;
        }

        ItemGem item = ModItems.getGemItem(gemId);

        if (item == null) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = new ItemStack(item);

        CompoundTag tag = new CompoundTag();

        tag.putString("GemType", gemId.toString());
        tag.putString("InstanceId", instanceId.toString());
        tag.putFloat("Quality", quality);
        tag.putInt("Variant", variant);

        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        return stack;
    }

    public static int generateRandomVariant(Identifier gemId, RandomSource random) {
        if (gemId == null) {
            return -1;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null || definition.getVariants().isEmpty()) {
            return -1;
        }

        List<GemVariant> variants = definition.getVariants();

        return variants.get(random.nextInt(variants.size())).getId();
    }

    public void poof(Player player) {
        if (!hasOwner() || !player.getUUID().equals(getOwnerUUID())) {
            return;
        }

        ItemStack stack = createGemItem();

        if (!stack.isEmpty()) {
            spawnAtLocation((ServerLevel) level(), stack);
        }

        this.playSound(ModSounds.POOF.get(), 1.0F, 1.0F);

        discard();
    }

    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : gemInventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return gemInventory.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = ContainerHelper.removeItem(gemInventory, index, count);

        if (!stack.isEmpty()) {
            setChanged();
        }

        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = gemInventory.get(index);
        gemInventory.set(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        gemInventory.set(index, stack);

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
        return isAlive()
                && getOwnerUUID() != null
                && getOwnerUUID().equals(player.getUUID())
                && player.distanceToSqr(this) <= 64.0;
    }

    @Override
    public void clearContent() {
        gemInventory.clear();
    }

    @Override
    public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {

    }

    @Override
    public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int i1) {

    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GemUIContainer(id, inventory, this);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, net.minecraft.network.RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(getId());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(getGemName());
    }


    public SoundEvent getInstrument() {
        GemDefinition definition = getGemDefinition();

        if (definition == null) {
            return SoundEvents.NOTE_BLOCK_HARP.value();
        }

        return BuiltInRegistries.SOUND_EVENT.getValue(definition.getInstrumentSound());
    }

    protected SoundEvent getAmbientSound() {
        return getInstrument();
    }

    protected SoundEvent getHurtSound(DamageSource p_30424_) {
        return getInstrument();
    }


    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.POOF.get();
    }

    @Override
    protected void playHurtSound(DamageSource p_21160_) {
        this.playSound(getInstrument(), this.getSoundVolume(), this.hurtPitch());
    }

    public float interactPitch() {
        return (float) (1 + random.nextFloat() * (1.5 - 1));
    }

    public float hurtPitch() {
        return (float) (0.25 + random.nextFloat() * (0.75 - 0.25));
    }


    public void copyInventoryTo(EntityFusion fusion, int offset) {
        for (int i = 0; i < getContainerSize(); i++) {
            fusion.setItem(offset + i, getItem(i).copy());
        }
    }

    public void clearInventory() {
        gemInventory.clear();
    }

}
