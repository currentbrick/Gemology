package com.currentbrick.gemology.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SetGemTabPayload(int tab) implements CustomPacketPayload {

    public static final Type<SetGemTabPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath("gemology", "set_gem_tab"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetGemTabPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buffer, payload) -> buffer.writeVarInt(payload.tab()),
                    buffer -> new SetGemTabPayload(buffer.readVarInt())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}