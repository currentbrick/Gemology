package com.currentbrick.gemology.container;

import com.currentbrick.gemology.blockentity.IncubatorBE;
import com.currentbrick.gemology.init.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class IncubatorContainer extends AbstractContainerMenu {

    private final IncubatorBE incubator;

    private final ContainerData data;

    public IncubatorContainer(int containerId, Inventory playerInventory, IncubatorBE incubator, ContainerData data) {
        super(ModContainers.INCUBATOR_CONTAINER.get(), containerId);
        this.incubator = incubator;
        this.data = data;
        checkContainerDataCount(data, 2);

        addDataSlots(data);

        // Cruxes
        int[][] cruxPositions = {
                {38, 27},
                {76, 19},
                {114, 27},
                {30, 65},
                {122, 65},
                {38, 103},
                {76, 111},
                {114, 103}
        };

        for (int i = 0; i < 8; i++) {
            addSlot(new Slot(
                    incubator,
                    i,
                    cruxPositions[i][0],
                    cruxPositions[i][1]
            ));
        }

        // Gem base
        addSlot(new Slot(incubator, 8, 76, 65));

        // Essences
        addSlot(new Slot(incubator, 9, 168, 35));
        addSlot(new Slot(incubator, 10, 168, 53));

        // Chroma
        addSlot(new Slot(incubator, 11, 168, 17));

        // Output
        addSlot(new Slot(incubator, IncubatorBE.OUTPUT_SLOT, 163, 110) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(
                        playerInventory,
                        column + row * 9 + 9,
                        24 + column * 18,
                        141 + row * 18
                ));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(
                    playerInventory,
                    column,
                    24 + column * 18,
                    199
            ));
        }
    }

    public IncubatorContainer(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, (IncubatorBE) playerInventory.player.level().getBlockEntity(data.readBlockPos()), new SimpleContainerData(2));
    }

    public int getIncubationProgress() {
        return data.get(0);
    }

    public int getIncubationTime() {
        return data.get(1);
    }

    @Override
    public boolean stillValid(Player player) {
        return incubator != null && incubator.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    public BlockPos getBlockPos() {
        return incubator.getBlockPos();
    }
}