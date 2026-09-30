package com.currentbrick.gemology.container;

import com.currentbrick.gemology.entities.EntityGem;
import com.currentbrick.gemology.init.ModContainers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GemUIContainer extends AbstractContainerMenu {

    private int selectedTab = 0;

    private final DataSlot selectedTabData = DataSlot.standalone();

    public final EntityGem gem;

    public GemUIContainer(int windowID, Inventory playerInventory, EntityGem gem) {
        super(ModContainers.GEM_UI_CONTAINER.get(), windowID);

        this.gem = gem;

        addDataSlot(selectedTabData);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int slot = col + row * 4;
                this.addSlot(new GemInventorySlot(this, gem, slot, 69 + col * 18, 47 + row * 18));
            }
        }

        this.addSlot(new GemEquipmentSlot(this, gem, EquipmentSlot.HEAD, 84, 37));
        this.addSlot(new GemEquipmentSlot(this, gem, EquipmentSlot.CHEST, 84, 55));
        this.addSlot(new GemEquipmentSlot(this, gem, EquipmentSlot.LEGS, 84, 73));
        this.addSlot(new GemEquipmentSlot(this, gem, EquipmentSlot.FEET, 84, 91));
        this.addSlot(new GemEquipmentSlot(this, gem, EquipmentSlot.MAINHAND, 84, 109));


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(
                        playerInventory,
                        col + row * 9 + 9,
                        24 + col * 18,
                        141 + row * 18
                ));
            }
        }

        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(
                    playerInventory,
                    col,
                    24 + col * 18,
                    199
            ));
        }
    }

    public GemUIContainer(int windowID, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(windowID, playerInventory, getGem(playerInventory, extraData));
    }

    private static EntityGem getGem(Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        int entityId = extraData.readInt();

        Entity entity = playerInventory.player.level().getEntity(entityId);

        if (entity instanceof EntityGem gem) {
            return gem;
        }

        throw new IllegalStateException("Entity is not an EntityGem");
    }

    @Override
    public boolean stillValid(Player player) {
        return gem.isAlive() && gem.getOwnerUUID() != null && gem.getOwnerUUID().equals(player.getUUID()) && player.distanceToSqr(gem) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack source = slot.getItem();
        ItemStack original = source.copy();

        if (index >= 0 && index < 16) {
            if (!moveItemStackTo(source, 21, 57, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (index >= 16 && index <= 20) {
            if (!moveItemStackTo(source, 21, 57, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (index >= 20 && index < 57) {

            if (selectedTab == 0) {
                boolean equipped = false;

                if (source.canEquip(EquipmentSlot.HEAD, gem)) {
                    equipped = moveItemStackTo(source, 16, 17, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.CHEST, gem)) {
                    equipped = moveItemStackTo(source, 17, 18, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.LEGS, gem)) {
                    equipped = moveItemStackTo(source, 18, 19, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.FEET, gem)) {
                    equipped = moveItemStackTo(source, 19, 20, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.MAINHAND, gem)) {
                    equipped = moveItemStackTo(source, 20, 21, false);
                }

                if (equipped) {

                } else {
                    return ItemStack.EMPTY;
                }
            }
            else if (selectedTab == 1) {
                if (!moveItemStackTo(source, 0, 16, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (source.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setByPlayer(source);
        }

        return original;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
        this.selectedTabData.set(selectedTab);
    }

    @Override
    public void setData(int id, int value) {
        super.setData(id, value);

        if (id == 0) {
            selectedTab = value;
        }
    }
}