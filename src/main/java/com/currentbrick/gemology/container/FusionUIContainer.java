package com.currentbrick.gemology.container;

import com.currentbrick.gemology.entity.EntityFusion;
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

public class FusionUIContainer extends AbstractContainerMenu {

    private int selectedTab = 0;

    private final DataSlot selectedTabData = DataSlot.standalone();

    public final EntityFusion fusion;

    public FusionUIContainer(int windowID, Inventory playerInventory, EntityFusion fusion) {
        super(ModContainers.FUSION_UI_CONTAINER.get(), windowID);

        this.fusion = fusion;

        addDataSlot(selectedTabData);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 8; col++) {
                int slot = col + row * 8;
                this.addSlot(new FusionInventorySlot(this, fusion, slot, 33 + col * 18, 47 + row * 18));
            }
        }

        this.addSlot(new FusionEquipmentSlot(this, fusion, EquipmentSlot.HEAD, 84, 37));
        this.addSlot(new FusionEquipmentSlot(this, fusion, EquipmentSlot.CHEST, 84, 55));
        this.addSlot(new FusionEquipmentSlot(this, fusion, EquipmentSlot.LEGS, 84, 73));
        this.addSlot(new FusionEquipmentSlot(this, fusion, EquipmentSlot.FEET, 84, 91));
        this.addSlot(new FusionEquipmentSlot(this, fusion, EquipmentSlot.MAINHAND, 84, 109));


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

    public FusionUIContainer(int windowID, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(windowID, playerInventory, getGem(playerInventory, extraData));
    }

    private static EntityFusion getGem(Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        int entityId = extraData.readInt();

        Entity entity = playerInventory.player.level().getEntity(entityId);

        if (entity instanceof EntityFusion fusion) {
            return fusion;
        }

        throw new IllegalStateException("Entity is not an EntityFusion");
    }

    @Override
    public boolean stillValid(Player player) {
        return fusion.isAlive() /*&& fusion.getOwnerUUID() != null && fusion.getOwnerUUID().equals(player.getUUID()) */&& player.distanceToSqr(fusion) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack source = slot.getItem();
        ItemStack original = source.copy();

        if (index >= 0 && index < 32) {
            if (!moveItemStackTo(source, 21, 57, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (index >= 32 && index <= 36) {
            if (!moveItemStackTo(source, 37, 57, true)) {
                return ItemStack.EMPTY;
            }
        }

        else if (index >= 36 && index < 73) {

            if (selectedTab == 0) {
                boolean equipped = false;

                if (source.canEquip(EquipmentSlot.HEAD, fusion)) {
                    equipped = moveItemStackTo(source, 32, 33, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.CHEST, fusion)) {
                    equipped = moveItemStackTo(source, 33, 34, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.LEGS, fusion)) {
                    equipped = moveItemStackTo(source, 34, 35, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.FEET, fusion)) {
                    equipped = moveItemStackTo(source, 35, 36, false);
                }

                if (!equipped && source.canEquip(EquipmentSlot.MAINHAND, fusion)) {
                    equipped = moveItemStackTo(source, 36, 37, false);
                }

                if (equipped) {

                } else {
                    return ItemStack.EMPTY;
                }
            }
            else if (selectedTab == 1) {
                if (!moveItemStackTo(source, 0, 32, false)) {
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