package com.currentbrick.gemology.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class FusionItem extends Item {
    public FusionItem(Properties properties) {
        super(properties.stacksTo(1).durability(5));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }
}
