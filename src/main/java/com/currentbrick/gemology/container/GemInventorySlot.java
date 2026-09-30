package com.currentbrick.gemology.container;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class GemInventorySlot extends Slot {

    private final GemUIContainer menu;

    public GemInventorySlot(GemUIContainer menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public boolean isActive() {
        return menu.getSelectedTab() == 1;
    }
}