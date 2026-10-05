package com.currentbrick.gemology.item;

import com.currentbrick.gemology.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ChromaCatalystItem extends Item {

    public ChromaCatalystItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        BlockPos clickedPos = context.getClickedPos();
        Direction face = context.getClickedFace();

        if (face != Direction.UP) {
            return InteractionResult.PASS;
        }

        BlockPos plantPos = clickedPos.above();

        if (!level.getBlockState(plantPos).canBeReplaced()) {
            return InteractionResult.PASS;
        }

        BlockState plantState = ModBlocks.CHROMA_CLUSTER_CROP.get().defaultBlockState();

        if (!level.isClientSide()) {
            level.setBlock(plantPos, plantState, Block.UPDATE_ALL);

            Player player = context.getPlayer();

            if (player == null || !player.getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}