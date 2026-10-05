package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.block.ChromaBlock;
import com.currentbrick.gemology.block.ChromaClusterBlock;
import com.currentbrick.gemology.block.DrainedBlock;
import com.currentbrick.gemology.block.IncubatorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Gemology.MODID);

    public static final DeferredBlock<Block> TUNGSTEN_ORE = BLOCKS.registerSimpleBlock("tungsten_ore",
            p -> p.ofFullCopy(Blocks.IRON_ORE));

    public static final DeferredBlock<Block> DEEPSLATE_TUNGSTEN_ORE = BLOCKS.registerSimpleBlock("deepslate_tungsten_ore",
            p -> p.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));

    public static final DeferredBlock<Block> TUNGSTEN_BLOCK = BLOCKS.registerSimpleBlock("tungsten_block",
            p -> p.ofFullCopy(Blocks.IRON_BLOCK));

    public static final DeferredBlock<Block> RAW_TUNGSTEN_BLOCK = BLOCKS.registerSimpleBlock("raw_tungsten_block",
            p -> p.ofFullCopy(Blocks.RAW_IRON_BLOCK));

    public static final DeferredBlock<Block> DRAINED_STONE = BLOCKS.registerBlock("drained_stone",
            p -> new DrainedBlock(p.strength(1.5F, 6.0F).requiresCorrectToolForDrops().randomTicks()));

    public static final DeferredBlock<Block> DRAINED_SOIL = BLOCKS.registerBlock("drained_soil",
            p -> new DrainedBlock(p.strength(0.5F).randomTicks()));


    public static final DeferredBlock<Block> INCUBATOR = BLOCKS.registerBlock("incubator",
            p -> new IncubatorBlock(p.strength(5.0F, 6.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> CHROMA_CLUSTER_CROP = BLOCKS.registerBlock("chroma_cluster_crop",
            ChromaClusterBlock::new, p -> p.randomTicks().noOcclusion().instabreak().sound(SoundType.AMETHYST));


    public static final DeferredBlock<Block> WHITE_CHROMA_CRYSTAL = BLOCKS.registerBlock("white_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 0)
    );
    public static final DeferredBlock<Block> ORANGE_CHROMA_CRYSTAL = BLOCKS.registerBlock("orange_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 1)
    );

    public static final DeferredBlock<Block> MAGENTA_CHROMA_CRYSTAL = BLOCKS.registerBlock("magenta_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 2)
    );

    public static final DeferredBlock<Block> LIGHT_BLUE_CHROMA_CRYSTAL = BLOCKS.registerBlock("light_blue_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 3)
    );

    public static final DeferredBlock<Block> YELLOW_CHROMA_CRYSTAL = BLOCKS.registerBlock("yellow_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 4)
    );

    public static final DeferredBlock<Block> LIME_CHROMA_CRYSTAL = BLOCKS.registerBlock("lime_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 5)
    );

    public static final DeferredBlock<Block> PINK_CHROMA_CRYSTAL = BLOCKS.registerBlock("pink_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 6)
    );

    public static final DeferredBlock<Block> GRAY_CHROMA_CRYSTAL = BLOCKS.registerBlock("gray_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 7)
    );

    public static final DeferredBlock<Block> LIGHT_GRAY_CHROMA_CRYSTAL = BLOCKS.registerBlock("light_gray_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()
                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 8)
    );

    public static final DeferredBlock<Block> CYAN_CHROMA_CRYSTAL = BLOCKS.registerBlock("cyan_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 9)
    );

    public static final DeferredBlock<Block> PURPLE_CHROMA_CRYSTAL = BLOCKS.registerBlock("purple_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 10)
    );

    public static final DeferredBlock<Block> BLUE_CHROMA_CRYSTAL = BLOCKS.registerBlock("blue_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 11)
    );

    public static final DeferredBlock<Block> BROWN_CHROMA_CRYSTAL = BLOCKS.registerBlock("brown_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 12)
    );

    public static final DeferredBlock<Block> GREEN_CHROMA_CRYSTAL = BLOCKS.registerBlock("green_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 13)
    );

    public static final DeferredBlock<Block> RED_CHROMA_CRYSTAL = BLOCKS.registerBlock("red_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 14)
    );

    public static final DeferredBlock<Block> BLACK_CHROMA_CRYSTAL = BLOCKS.registerBlock("black_chroma_crystal", p ->
            new ChromaBlock(p.strength(3.0f, 6.0f)
                    .sound(SoundType.GLASS)
                    .requiresCorrectToolForDrops()

                    .lightLevel((state) -> {
                        return 9;
                    })
                    , 15)
    );
}
