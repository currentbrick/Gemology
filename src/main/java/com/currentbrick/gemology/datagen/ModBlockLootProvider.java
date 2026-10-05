package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModItems;
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {

    public ModBlockLootProvider(Context output) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.TUNGSTEN_BLOCK.get());
        dropSelf(ModBlocks.RAW_TUNGSTEN_BLOCK.get());
        add(ModBlocks.TUNGSTEN_ORE.get(),
                block -> createOreDrop(ModBlocks.TUNGSTEN_ORE.get(), ModItems.RAW_TUNGSTEN.get()));
        add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get(), ModItems.RAW_TUNGSTEN.get()));
        dropSelf(ModBlocks.DRAINED_STONE.get());
        dropSelf(ModBlocks.DRAINED_SOIL.get());
        dropSelf(ModBlocks.INCUBATOR.get());
        add(ModBlocks.CHROMA_CLUSTER_CROP.get(), noDrop());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>(ModBlocks.BLOCKS.getEntries().stream().map(Holder::value).toList());
        list.remove(ModBlocks.RED_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.ORANGE_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.YELLOW_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.LIME_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.GREEN_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.CYAN_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.BLUE_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.MAGENTA_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.PURPLE_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.PINK_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.BROWN_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.WHITE_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.GRAY_CHROMA_CRYSTAL.get());
        list.remove(ModBlocks.BLACK_CHROMA_CRYSTAL.get());
        return list::iterator;
    }
}
