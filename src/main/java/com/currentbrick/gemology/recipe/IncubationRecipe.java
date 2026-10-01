package com.currentbrick.gemology.recipe;

import com.currentbrick.gemology.init.ModRecipeSerializers;
import com.currentbrick.gemology.init.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public record IncubationRecipe(Identifier gem, Ingredient gemBase, Ingredient chroma, List<CruxRequirement> cruxes, Ingredient essence1, Ingredient essence2, int incubationTime) implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (!(input instanceof IncubationInput incubationInput)) {
            return false;
        }

        // Base gem
        if (incubationInput.gemBase().isEmpty()) {
            return false;
        }

        // Chroma
        if (!chroma.test(incubationInput.chroma())) {
            return false;
        }

        // Essences
        if (!essence1.test(incubationInput.essence1())) {
            return false;
        }

        if (!essence2.test(incubationInput.essence2())) {
            return false;
        }

        if (!gemBase.test(incubationInput.gemBase())) {
            return false;
        }

        // Eight cruxes
        ItemStack[] cruxStacks = {
                incubationInput.crux1(),
                incubationInput.crux2(),
                incubationInput.crux3(),
                incubationInput.crux4(),
                incubationInput.crux5(),
                incubationInput.crux6(),
                incubationInput.crux7(),
                incubationInput.crux8()
        };

        for (ItemStack stack : cruxStacks) {
            if (stack.isEmpty()) {
                return false;
            }

            boolean matchesCrux = cruxes.stream()
                    .anyMatch(requirement -> requirement.ingredient().test(stack));

            if (!matchesCrux) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "incubation";
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipeSerializers.INCUBATION.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.INCUBATION.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return new RecipeBookCategory();
    }

    public static final MapCodec<IncubationRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Identifier.CODEC
                            .fieldOf("gem")
                            .forGetter(IncubationRecipe::gem),

                    Ingredient.CODEC
                            .fieldOf("gemBase")
                            .forGetter(IncubationRecipe::gemBase),

                    Ingredient.CODEC
                            .fieldOf("chroma")
                            .forGetter(IncubationRecipe::chroma),

                    CruxRequirement.CODEC
                            .listOf()
                            .fieldOf("cruxes")
                            .forGetter(IncubationRecipe::cruxes),

                    Ingredient.CODEC
                            .fieldOf("essence1")
                            .forGetter(IncubationRecipe::essence1),

                    Ingredient.CODEC
                            .fieldOf("essence2")
                            .forGetter(IncubationRecipe::essence2),
                    Codec.INT
                            .fieldOf("incubationTime")
                            .forGetter(IncubationRecipe::incubationTime)
            ).apply(instance, IncubationRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IncubationRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Identifier.STREAM_CODEC,
                    IncubationRecipe::gem,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    IncubationRecipe::gemBase,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    IncubationRecipe::chroma,

                    ByteBufCodecs.collection(
                            java.util.ArrayList::new,
                            CruxRequirement.STREAM_CODEC
                    ),
                    IncubationRecipe::cruxes,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    IncubationRecipe::essence1,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    IncubationRecipe::essence2,

                    ByteBufCodecs.VAR_INT,
                    IncubationRecipe::incubationTime,

                    IncubationRecipe::new
            );

}