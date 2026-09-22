package com.currentbrick.gempire.init;

import com.currentbrick.gempire.Gemology;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Gemology.MODID);

    public static final DeferredBlock<Block> TUNGSTEN_ORE = BLOCKS.registerSimpleBlock("tungsten_ore",
            p -> p.ofFullCopy(Blocks.IRON_ORE));

    public static final DeferredBlock<Block> DEEPSLATE_TUNGSTEN_ORE = BLOCKS.registerSimpleBlock("deepslate_tungsten_ore",
            p -> p.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));
}
