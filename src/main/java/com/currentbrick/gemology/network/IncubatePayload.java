package com.currentbrick.gemology.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record IncubatePayload(BlockPos pos) implements CustomPacketPayload {

    public static final Type<IncubatePayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(
                    "gemology",
                    "incubate"
            ));

    public static final StreamCodec<RegistryFriendlyByteBuf, IncubatePayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    IncubatePayload::pos,
                    IncubatePayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}