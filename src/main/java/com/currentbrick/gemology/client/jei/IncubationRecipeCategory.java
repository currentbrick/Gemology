package com.currentbrick.gemology.client.jei;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.init.ModItems;
import com.currentbrick.gemology.recipe.CruxRequirement;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IncubationRecipeCategory extends AbstractRecipeCategory<IncubationRecipe> {

    private final IDrawable background;

    public IncubationRecipeCategory(IGuiHelper guiHelper) {
        super(
                GemologyJEIPlugin.INCUBATION_RECIPE_TYPE,
                Component.translatable("jei.gemology.incubation"),
                guiHelper.createDrawableIngredient(
                        VanillaTypes.ITEM_STACK,
                        ModItems.INCUBATOR.get().getDefaultInstance()
                ),
                171,
                117
        );

        background = guiHelper.drawableBuilder(
                Identifier.fromNamespaceAndPath(
                        Gemology.MODID,
                        "textures/gui/incubator_jei.png"
                ),
                0,
                0,
                171,
                117
        ).setTextureSize(171, 117).build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncubationRecipe recipe, IFocusGroup focuses) {
        // Gem base
        builder.addInputSlot(48, 50)
                .add(recipe.gemBase());

        // 28, 15
        // Chroma
        builder.addInputSlot(140, 2)
                .add(recipe.chroma());

        // Essence 1
        builder.addInputSlot(140, 20)
                .add(recipe.essence1());

        // Essence 2
        builder.addInputSlot(140, 38)
                .add(recipe.essence2());

        // Cruxes
        int[][] cruxPositions = {
                {10, 12},
                {48, 4},
                {86, 12},
                {2, 50},
                {94, 50},
                {10, 88},
                {48, 96},
                {86, 88}
        };

        for (int i = 0; i < cruxPositions.length; i++) {
            IRecipeSlotBuilder slot = builder.addInputSlot(
                    cruxPositions[i][0],
                    cruxPositions[i][1]
            );

            for (CruxRequirement crux : recipe.cruxes()) {
                slot.add(crux.ingredient());
            }
        }

        // Output
        Item gemItem = ModItems.getGemItem(recipe.gem());

        if (gemItem != null) {
            builder.addOutputSlot(135, 95).add(new ItemStack(gemItem));
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

    @Override
    public void draw(IncubationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }
}