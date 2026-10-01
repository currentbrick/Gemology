package com.currentbrick.gemology.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record IncubationInput(
        ItemStack gemBase,
        ItemStack essence1,
        ItemStack essence2,
        ItemStack chroma,
        ItemStack crux1,
        ItemStack crux2,
        ItemStack crux3,
        ItemStack crux4,
        ItemStack crux5,
        ItemStack crux6,
        ItemStack crux7,
        ItemStack crux8
) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> gemBase;
            case 1 -> essence1;
            case 2 -> essence2;
            case 3 -> chroma;
            case 4 -> crux1;
            case 5 -> crux2;
            case 6 -> crux3;
            case 7 -> crux4;
            case 8 -> crux5;
            case 9 -> crux6;
            case 10 -> crux7;
            case 11 -> crux8;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 12;
    }
}