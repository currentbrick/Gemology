package com.currentbrick.gemology;

import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemDefinitionLoader;
import com.currentbrick.gemology.entity.gem.GemDefinitionManager;
import com.currentbrick.gemology.entity.gem.abilities.AbilityDefinitionLoader;
import com.currentbrick.gemology.entity.gem.abilities.AbilityManager;
import com.currentbrick.gemology.init.*;
import com.currentbrick.gemology.item.ItemGem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Gemology.MODID)
public class Gemology {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "gemology";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final GemDefinitionManager GEM_DEFINITION_MANAGER = new GemDefinitionManager();
    public static final AbilityManager ABILITY_MANAGER = new AbilityManager();

    public Gemology(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModFeatures.FEATURE_TYPES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModContainers.MENUS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        ModRecipeTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::createAttributes);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModAbilities.register();
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(ModItems.EXAMPLE_BLOCK_ITEM);
        }
    }

    @SubscribeEvent
    public void addReloadListeners(AddServerReloadListenersEvent event) {
        event.addListener(Identifier.fromNamespaceAndPath(MODID, "gem_definitions"), new GemDefinitionLoader(GEM_DEFINITION_MANAGER));
        event.addListener(Identifier.fromNamespaceAndPath(MODID, "ability_definitions"), new AbilityDefinitionLoader(ABILITY_MANAGER));
    }

    public void createAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEM.get(), ModEntityAttributes.createGemAttributes().build());
        event.put(ModEntities.FUSION.get(), ModEntityAttributes.createFusionAttributes().build());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof ItemEntity itemEntity)) {
            return;
        }

        if (!(itemEntity.getItem().getItem() instanceof ItemGem gemItem)) {
            return;
        }

        if (itemEntity.level().isClientSide()) {
            return;
        }

        CompoundTag data = itemEntity.getPersistentData();

        if (!data.contains("Reforming")) {
            data.putBoolean("Reforming", true);
            data.putInt("ReformationTimer", 0);

            System.out.println("REFORMATION STARTED: " + gemItem.getGemId());
        }

        int timer = data.getInt("ReformationTimer").orElse(0);
        timer++;

        data.putInt("ReformationTimer", timer);

        if (timer > 40 && timer < 80) {
            itemEntity.setNoGravity(true);
            itemEntity.setDeltaMovement(0, 0.075, 0);


            if (itemEntity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.END_ROD, itemEntity.getX(), itemEntity.getY() + 0.3, itemEntity.getZ(), 3, 0.25, 0.25, 0.25, 0.02);
            }
            itemEntity.setGlowingTag(true);
        } else if (timer > 80) {
            EntityGem gem = gemItem.createGem(event.getEntity().level(), itemEntity.getItem());
            if (gem != null) {
                BlockPos pos = event.getEntity().getOnPos();
                gem.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                event.getEntity().level().addFreshEntity(gem);
            }
            itemEntity.discard();
        }
    }

    @SubscribeEvent
    public void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(ModRecipeTypes.INCUBATION.get());
    }
}
