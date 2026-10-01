package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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
                output.accept(ModItems.TUNGSTEN_BLOCK.get());
                output.accept(ModItems.TUNGSTEN_ORE.get());
                output.accept(ModItems.DEEPSLATE_TUNGSTEN_ORE.get());

                output.accept(ModItems.INCUBATOR.get());

                output.accept(ModItems.WHITE_CHROMA.get());
                output.accept(ModItems.LIGHT_GRAY_CHROMA.get());
                output.accept(ModItems.GRAY_CHROMA.get());
                output.accept(ModItems.BLACK_CHROMA.get());
                output.accept(ModItems.BROWN_CHROMA.get());
                output.accept(ModItems.RED_CHROMA.get());
                output.accept(ModItems.ORANGE_CHROMA.get());
                output.accept(ModItems.YELLOW_CHROMA.get());
                output.accept(ModItems.LIME_CHROMA.get());
                output.accept(ModItems.GREEN_CHROMA.get());
                output.accept(ModItems.CYAN_CHROMA.get());
                output.accept(ModItems.LIGHT_BLUE_CHROMA.get());
                output.accept(ModItems.BLUE_CHROMA.get());
                output.accept(ModItems.PURPLE_CHROMA.get());
                output.accept(ModItems.MAGENTA_CHROMA.get());
                output.accept(ModItems.PINK_CHROMA.get());
                output.accept(ModItems.PRISMATIC_CHROMA.get());

                output.accept(ModBlocks.WHITE_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.GRAY_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.BLACK_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.BROWN_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.RED_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.ORANGE_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.YELLOW_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.LIME_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.GREEN_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.CYAN_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.BLUE_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.PURPLE_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.MAGENTA_CHROMA_CRYSTAL.get());
                output.accept(ModBlocks.PINK_CHROMA_CRYSTAL.get());

                output.accept(ModItems.WHITE_ESSENCE.get());
                output.accept(ModItems.YELLOW_ESSENCE.get());
                output.accept(ModItems.BLUE_ESSENCE.get());
                output.accept(ModItems.PINK_ESSENCE.get());
                output.accept(ModItems.FUSION.get());

                output.accept(ModItems.INACTIVE_RUBY_BASE.get());
                output.accept(ModItems.INACTIVE_JASPER_BASE.get());

                output.accept(ModItems.RUBY.get());

                addGemVariants(output, ModItems.JASPER.get(), 16);
                addGemVariants(output, ModItems.QUARTZ.get(), 17);
            }).build());


    private static ItemStack createGemVariantStack(Item item, int variant) {
        ItemStack stack = new ItemStack(item);

        CompoundTag tag = new CompoundTag();
        tag.putInt("Variant", variant);

        stack.set(
                DataComponents.CUSTOM_DATA,
                CustomData.of(tag)
        );

        return stack;
    }

    private static void addGemVariants(CreativeModeTab.Output output, Item item, int variantCount) {
        for (int i = 0; i < variantCount; i++) {
            output.accept(createGemVariantStack(item, i));
        }
    }
}
