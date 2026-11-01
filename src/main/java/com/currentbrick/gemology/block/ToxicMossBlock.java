package com.currentbrick.gemology.block;

import com.currentbrick.gemology.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ToxicMossBlock extends BushBlock {

    public ToxicMossBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0) {
            summonClod(level, pos, random);
            super.randomTick(state, level, pos, random);
        }
    }

    public void summonClod(ServerLevel level, BlockPos pos, RandomSource random) {
        long nearbyClods = level.getEntitiesOfClass(Spider.class, new AABB(pos).inflate(16)).size();
        if (nearbyClods >= 2) return;
        Spider spider = EntityTypes.SPIDER.create(level, EntitySpawnReason.MOB_SUMMONED);

        if (spider == null) return;

        BlockPos spawnPos = pos.above();

        spider.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
        spider.setYRot(random.nextInt(360));

        if (level.noCollision(spider) && level.getBlockState(spawnPos).isAir()) {
            level.addFreshEntity(spider);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(ModBlocks.DRAINED_SOIL);
    }
}
