package com.currentbrick.gemology.entities;

import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class EntityGem extends Monster implements RangedAttackMob, Container, MenuProvider, ContainerListener {

    protected EntityGem(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float v) {

    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public void slotChanged(AbstractContainerMenu abstractContainerMenu, int i, ItemStack itemStack) {

    }

    @Override
    public void dataChanged(AbstractContainerMenu abstractContainerMenu, int i, int i1) {

    }
}
