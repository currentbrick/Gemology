package com.currentbrick.gemology.client.jei;

import com.currentbrick.gemology.init.ModRecipeTypes;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class IncubationJEIRecipes {

    private static final List<IncubationRecipe> RECIPES = new ArrayList<>();

    public static List<IncubationRecipe> getRecipes() {
        return List.copyOf(RECIPES);
    }

    public static void update(
            Iterable<RecipeHolder<IncubationRecipe>> recipes
    ) {
        RECIPES.clear();

        for (RecipeHolder<IncubationRecipe> recipe : recipes) {
            RECIPES.add(recipe.value());
        }
    }

    public static void clear() {
        RECIPES.clear();
    }
}