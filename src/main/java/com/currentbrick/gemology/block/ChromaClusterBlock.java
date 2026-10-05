package com.currentbrick.gemology.block;

import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.tags.BlockTags;

public class ChromaClusterBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);

    public ChromaClusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return false;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.CHROMA_CATALYST.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 0;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);

        if (age < 5) {
            if (random.nextInt(25) == 0) {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
            }
            return;
        }

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        BlockPos targetPos = pos.relative(direction);

        BlockState groundState = level.getBlockState(targetPos.below());

        if (!level.isEmptyBlock(targetPos)) {
            return;
        }

        if (!groundState.is(BlockTags.BASE_STONE_OVERWORLD)) {
            return;
        }

        BlockState crystalState = switch (random.nextInt(16)) {
            case 0 -> ModBlocks.RED_CHROMA_CRYSTAL.get().defaultBlockState();
            case 1 -> ModBlocks.ORANGE_CHROMA_CRYSTAL.get().defaultBlockState();
            case 2 -> ModBlocks.YELLOW_CHROMA_CRYSTAL.get().defaultBlockState();
            case 3 -> ModBlocks.LIME_CHROMA_CRYSTAL.get().defaultBlockState();
            case 4 -> ModBlocks.GREEN_CHROMA_CRYSTAL.get().defaultBlockState();
            case 5 -> ModBlocks.CYAN_CHROMA_CRYSTAL.get().defaultBlockState();
            case 6 -> ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.get().defaultBlockState();
            case 7 -> ModBlocks.BLUE_CHROMA_CRYSTAL.get().defaultBlockState();
            case 8 -> ModBlocks.MAGENTA_CHROMA_CRYSTAL.get().defaultBlockState();
            case 9 -> ModBlocks.PURPLE_CHROMA_CRYSTAL.get().defaultBlockState();
            case 10 -> ModBlocks.PINK_CHROMA_CRYSTAL.get().defaultBlockState();
            case 11 -> ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.get().defaultBlockState();
            case 12 -> ModBlocks.GRAY_CHROMA_CRYSTAL.get().defaultBlockState();
            case 13 -> ModBlocks.BLACK_CHROMA_CRYSTAL.get().defaultBlockState();
            case 14 -> ModBlocks.WHITE_CHROMA_CRYSTAL.get().defaultBlockState();
            case 15 -> ModBlocks.BROWN_CHROMA_CRYSTAL.get().defaultBlockState();
            default -> throw new IllegalStateException();
        };

        level.setBlockAndUpdate(targetPos, crystalState);
    }
}