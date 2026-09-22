package com.currentbrick.gempire.init;

import com.currentbrick.gempire.Gemology;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "gemology" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gemology.MODID);



    // Creates a creative tab with the id "gemology:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.gemology")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.TUNGSTEN_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.RAW_TUNGSTEN.get());
                output.accept(ModItems.TUNGSTEN_INGOT.get());
                output.accept(ModItems.TUNGSTEN_NUGGET.get());
                output.accept(ModItems.TUNGSTEN_ORE.get());
                output.accept(ModItems.DEEPSLATE_TUNGSTEN_ORE.get());
            }).build());
}
