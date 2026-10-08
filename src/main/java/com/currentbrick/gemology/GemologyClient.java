package com.currentbrick.gemology;

import com.currentbrick.gemology.client.entity.FusionRenderer;
import com.currentbrick.gemology.client.entity.GemRenderer;
import com.currentbrick.gemology.client.item.GemVariantProperty;
import com.currentbrick.gemology.client.jei.IncubationJEIRecipes;
import com.currentbrick.gemology.client.screen.FusionUIScreen;
import com.currentbrick.gemology.client.screen.GemUIScreen;
import com.currentbrick.gemology.client.screen.IncubatorScreen;
import com.currentbrick.gemology.datagen.ModBlockLootProvider;
import com.currentbrick.gemology.datagen.ModBlockTagsProvider;
import com.currentbrick.gemology.datagen.ModModelProvider;
import com.currentbrick.gemology.datagen.ModRecipeProvider;
import com.currentbrick.gemology.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Gemology.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Gemology.MODID, value = Dist.CLIENT)
public class GemologyClient {
    public GemologyClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        Gemology.LOGGER.info("HELLO FROM CLIENT SETUP");
        Gemology.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {

        event.createProvider(ModModelProvider::new);

        event.createProvider(ModBlockTagsProvider::new);

        event.createReloadableRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.LOOT_TABLE, new LootTableProvider(
                                Set.of(),
                                List.of(
                                        new LootTableProvider.SubProviderEntry(
                                                ModBlockLootProvider::new,
                                                LootContextParamSets.BLOCK
                                        )
                                )
                        ))
                        .add(ModRecipeProvider.create())
        );
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GEM.get(), GemRenderer::new);
        event.registerEntityRenderer(ModEntities.FUSION.get(), FusionRenderer::new);
    }

    @SubscribeEvent
    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Gemology.MODID, "gem_variant"), GemVariantProperty.TYPE);
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModContainers.GEM_UI_CONTAINER.get(), GemUIScreen::new);
        event.register(ModContainers.FUSION_UI_CONTAINER.get(), FusionUIScreen::new);
        event.register(ModContainers.INCUBATOR_CONTAINER.get(), IncubatorScreen::new);
    }

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        IncubationJEIRecipes.update(
                event.getRecipeMap()
                        .byType(ModRecipeTypes.INCUBATION.get())
        );
    }

    @SubscribeEvent
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        IncubationJEIRecipes.clear();
    }
}
