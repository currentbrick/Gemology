package com.currentbrick.gemology.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;

public record CruxRequirement(
        Ingredient ingredient,
        float quality,
        float chance
) {

    public static final Codec<CruxRequirement> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Ingredient.CODEC
                            .fieldOf("ingredient")
                            .forGetter(CruxRequirement::ingredient),

                    Codec.FLOAT
                            .fieldOf("quality")
                            .forGetter(CruxRequirement::quality),

                    Codec.FLOAT
                            .fieldOf("chance")
                            .forGetter(CruxRequirement::chance)
            ).apply(instance, CruxRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CruxRequirement> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    CruxRequirement::ingredient,
                    StreamCodec.of(
                            (buffer, value) -> buffer.writeFloat(value),
                            RegistryFriendlyByteBuf::readFloat
                    ),
                    CruxRequirement::quality,
                    StreamCodec.of(
                            (buffer, value) -> buffer.writeFloat(value),
                            RegistryFriendlyByteBuf::readFloat
                    ),
                    CruxRequirement::chance,
                    CruxRequirement::new
            );
}