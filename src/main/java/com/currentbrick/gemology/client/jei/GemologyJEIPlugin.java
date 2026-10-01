package com.currentbrick.gemology.client.jei;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.init.ModRecipeTypes;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class GemologyJEIPlugin implements IModPlugin {

    public static final IRecipeType<IncubationRecipe> INCUBATION_RECIPE_TYPE =
            IRecipeType.create(
                    Gemology.MODID,
                    "incubation",
                    IncubationRecipe.class
            );

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(
                Gemology.MODID,
                "jei_plugin"
        );
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new IncubationRecipeCategory(
                        registration.getJeiHelpers().getGuiHelper()
                )
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                INCUBATION_RECIPE_TYPE,
                IncubationJEIRecipes.getRecipes()
        );
    }
}