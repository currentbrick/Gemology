package com.currentbrick.gemology.container;

import com.currentbrick.gemology.client.screen.FusionUIScreen;
import com.currentbrick.gemology.item.ItemGem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class FusionInventorySlot extends Slot {

    private final FusionUIContainer menu;

    public FusionInventorySlot(FusionUIContainer menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean isActive() {
        return menu.getSelectedTab() == 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof ItemGem) {
            return false;
        }

        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
            return false;
        }

        return super.mayPlace(stack);
    }
}