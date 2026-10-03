package com.currentbrick.gemology.recipe;

import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record ChromaRequirement(boolean acceptsAll, Ingredient ingredient) {

    public static ChromaRequirement all() {
        return new ChromaRequirement(true, null);
    }

    public static ChromaRequirement ingredient(Ingredient ingredient) {
        return new ChromaRequirement(false, ingredient);
    }

    public boolean matches(ItemStack stack, GemDefinition definition) {
        if (stack.isEmpty() || definition == null) {
            return false;
        }

        if (!acceptsAll && (ingredient == null || !ingredient.test(stack))) {
            return false;
        }

        Identifier chromaId =
                BuiltInRegistries.ITEM.getKey(stack.getItem());

        return definition.getVariants().stream()
                .anyMatch(variant ->
                        variant.getChromaId() != null && variant.getChromaId().equals(chromaId)
                );
    }

    public static final Codec<ChromaRequirement> CODEC =
            Codec.either(
                    Codec.STRING,
                    Ingredient.CODEC
            ).xmap(
                    either -> either.map(
                            string -> {
                                if (string.equals("all")) {
                                    return ChromaRequirement.all();
                                }

                                Identifier id = Identifier.parse(string);

                                Item item = BuiltInRegistries.ITEM.getValue(id);

                                return ChromaRequirement.ingredient(
                                        Ingredient.of(item)
                                );
                            },
                            ChromaRequirement::ingredient
                    ),
                    requirement -> {
                        if (requirement.acceptsAll()) {
                            return Either.left("all");
                        }

                        return Either.right(
                                requirement.ingredient()
                        );
                    }
            );

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            ChromaRequirement
            > STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    ChromaRequirement::acceptsAll,

                    ByteBufCodecs.optional(
                            Ingredient.CONTENTS_STREAM_CODEC
                    ),
                    requirement ->
                            java.util.Optional.ofNullable(
                                    requirement.ingredient()
                            ),

                    (acceptsAll, ingredient) ->
                            new ChromaRequirement(acceptsAll, ingredient.orElse(null))
            );
}