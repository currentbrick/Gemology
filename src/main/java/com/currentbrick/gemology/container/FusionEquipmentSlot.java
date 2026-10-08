package com.currentbrick.gemology.container;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FusionEquipmentSlot extends Slot {

    private final EquipmentSlot equipmentSlot;
    private final EntityFusion gem;
    private final FusionUIContainer menu;

    public FusionEquipmentSlot(FusionUIContainer menu, EntityFusion gem, EquipmentSlot equipmentSlot, int x, int y) {
        super(gem, 0, x, y);
        this.menu = menu;
        this.gem = gem;
        this.equipmentSlot = equipmentSlot;
    }

    @Override
    public ItemStack getItem() {
        return gem.getItemBySlot(equipmentSlot);
    }

    @Override
    public void set(ItemStack stack) {
        gem.setItemSlot(equipmentSlot, stack);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.canEquip(equipmentSlot, gem);
    }

    @Override
    public boolean mayPickup(Player player) {
        return !getItem().isEmpty();
    }

    @Override
    public boolean isActive() {
        return menu.getSelectedTab() == 0;
    }

    @Override
    public ItemStack remove(int amount) {
        ItemStack stack = getItem();

        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack result = stack.split(amount);

        gem.setItemSlot(equipmentSlot, stack);

        return result;
    }
}
