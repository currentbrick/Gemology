package com.currentbrick.gemology.client.jei;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.init.ModItems;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IncubationRecipeCategory extends AbstractRecipeCategory<IncubationRecipe> {

    public IncubationRecipeCategory(IGuiHelper guiHelper) {
        super(
                GemologyJEIPlugin.INCUBATION_RECIPE_TYPE,
                Component.translatable("jei.gemology.incubation"),
                guiHelper.createDrawable(
                        Identifier.fromNamespaceAndPath(
                                Gemology.MODID,
                                "textures/gui/incubator.png"
                        ),
                        0,
                        0,
                        208,
                        224
                ),
                208,
                224
        );
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            IncubationRecipe recipe,
            IFocusGroup focuses
    ) {
        // Gem base
        builder.addInputSlot(76, 65)
                .add(recipe.gemBase());

        // Chroma
        builder.addInputSlot(168, 17)
                .add(recipe.chroma());

        // Essence 1
        builder.addInputSlot(168, 35)
                .add(recipe.essence1());

        // Essence 2
        builder.addInputSlot(168, 53)
                .add(recipe.essence2());

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

        for (int i = 0; i < recipe.cruxes().size(); i++) {
            if (i >= cruxPositions.length) {
                break;
            }

            builder.addInputSlot(
                    cruxPositions[i][0],
                    cruxPositions[i][1]
            ).add(recipe.cruxes().get(i).ingredient());
        }

        // Output
        Item gemItem = ModItems.getGemItem(recipe.gem());

        if (gemItem != null) {
            builder.addOutputSlot(163, 110)
                    .add(new ItemStack(gemItem));
        }
    }

    @Override
    public void createRecipeExtras(
            IRecipeExtrasBuilder builder,
            IncubationRecipe recipe,
            IFocusGroup focuses
    ) {
        builder.addText(
                Component.literal(recipe.incubationTime() + " ticks"),
                0,
                0
        );
    }

    @Override
    public Identifier getIdentifier(IncubationRecipe recipe) {
        return Identifier.fromNamespaceAndPath(
                "gemology",
                "incubation/" + recipe.gem().getPath()
        );
    }
}