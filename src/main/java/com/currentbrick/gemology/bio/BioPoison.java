package com.currentbrick.gemology.bio;

import com.currentbrick.gemology.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;

public class BioPoison {

    public static void createInitialInfection(ServerLevel level, BlockPos centre, RandomSource random) {
        int radius = 3;
        int depth = 4;

        System.out.println("initial infection");

        for (int x = -radius; x <= radius; x++) {
            for (int y = -depth; y <= 0; y++) {
                for (int z = -radius; z <= radius; z++) {
                    double distance = Math.sqrt(x * x + z * z);
                    if (distance < radius) {
                        continue;
                    }

                    if (random.nextFloat() > 0.50F) {
                        continue;
                    }

                    BlockPos pos = centre.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    if (state.is(Blocks.STONE)) {
                        level.setBlock(pos, ModBlocks.DRAINED_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
                    } else if (state.is(BlockTags.DIRT) || state.is(Blocks.GRASS_BLOCK)) {
                        level.setBlock(pos, ModBlocks.DRAINED_SOIL.get().defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }
        }
    }
}