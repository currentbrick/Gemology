package com.currentbrick.gemology.block;

import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModDamageTypes;
import com.currentbrick.gemology.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DrainedBlock extends Block {

    public DrainedBlock(Properties properties) {
        super(properties);
    }

    private static final int SPREAD_CHANCE = 5;
    private static final int SPREAD_RANGE = 5;
    private static final int SPREAD_HEIGHT = 3;
    private static final int MOSS_GROWTH_CHANCE = 30;
    private static final int MOSS_GROWTH_RANGE = 3;

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(SPREAD_CHANCE) == 0) {
            BlockPos target = pos.offset(random.nextInt(SPREAD_RANGE) - 2, random.nextInt(SPREAD_HEIGHT) - 1, random.nextInt(SPREAD_RANGE) - 2);

            BlockState targetState = level.getBlockState(target);

            if (targetState.is(Blocks.STONE)) {
                level.setBlock(target, ModBlocks.DRAINED_STONE.get().defaultBlockState(), Block.UPDATE_ALL);
            } else if (targetState.is(BlockTags.DIRT) || targetState.is(Blocks.GRASS_BLOCK)) {
                level.setBlock(target, ModBlocks.DRAINED_SOIL.get().defaultBlockState(), Block.UPDATE_ALL);
            }
            super.randomTick(state, level, pos, random);
        }
        if (random.nextInt(MOSS_GROWTH_CHANCE) == 0) {
            tryGrowMoss(level, pos, random);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState onState, Entity entity) {
        if (!level.isClientSide() && entity.tickCount % 20 == 0) {
            ItemStack boots = entity instanceof LivingEntity living ? living.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY;
            if (boots.isEmpty() || !(boots.getItem() == Items.IRON_BOOTS || boots.getItem() == Items.DIAMOND_BOOTS || boots.getItem() == Items.NETHERITE_BOOTS)) {
                entity.hurt(ModDamageTypes.bioPoison((ServerLevel) level), 2.0F);
            }
        }

        super.stepOn(level, pos, onState, entity);
    }

    private void tryGrowMoss(ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(MOSS_GROWTH_CHANCE) != 0) return;

        int x = random.nextInt(MOSS_GROWTH_RANGE * 2 + 1) - MOSS_GROWTH_RANGE;
        int z = random.nextInt(MOSS_GROWTH_RANGE * 2 + 1) - MOSS_GROWTH_RANGE;

        BlockPos target = pos.offset(x, 1, z);

        if (!level.getBlockState(target).isAir()) return;

        BlockState mossState = ModBlocks.TOXIC_MOSS.get().defaultBlockState();

        if (mossState.canSurvive(level, target)) {
            level.setBlockAndUpdate(target, mossState);
        }
    }
}
