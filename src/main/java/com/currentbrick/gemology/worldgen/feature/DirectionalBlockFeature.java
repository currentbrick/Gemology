package com.currentbrick.gemology.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class DirectionalBlockFeature implements Feature {

    private final Holder<Block> toPlace;
    private final HolderSet<Block> canBePlacedOn;

    public static final MapCodec<DirectionalBlockFeature> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.BLOCK
                            .holderByNameCodec()
                            .fieldOf("to_place")
                            .forGetter(DirectionalBlockFeature::toPlace),
                    RegistryCodecs.holderSet(Registries.BLOCK, BuiltInRegistries.BLOCK.byNameCodec())
                            .fieldOf("can_be_placed_on")
                            .forGetter(DirectionalBlockFeature::canBePlacedOn)
            ).apply(instance, DirectionalBlockFeature::new));

    public DirectionalBlockFeature(Holder<Block> toPlace, HolderSet<Block> canBePlacedOn) {
        this.toPlace = toPlace;
        this.canBePlacedOn = canBePlacedOn;
    }

    @Override
    public MapCodec<? extends Feature> codec() {
        return CODEC;
    }

    public Holder<Block> toPlace() {
        return toPlace;
    }

    public HolderSet<Block> canBePlacedOn() {
        return canBePlacedOn;
    }

    @Override
    public boolean place(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource randomSource, BlockPos blockPos) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction dir : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(blockPos, dir);

            if (worldGenLevel.getBlockState(blockpos$mutableblockpos).is(canBePlacedOn)) {
                BlockState state = toPlace.value().defaultBlockState().setValue(DirectionalBlock.FACING, dir.getOpposite());
                this.setBlock(worldGenLevel, blockPos, state);
                return true;
            }
        }

        return false;
    }
}