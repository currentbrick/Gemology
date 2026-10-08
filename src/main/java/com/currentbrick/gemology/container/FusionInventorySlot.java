package com.currentbrick.gemology.container;

import com.currentbrick.gemology.client.screen.FusionUIScreen;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

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
}