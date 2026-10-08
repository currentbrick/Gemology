package com.currentbrick.gemology.network;

import com.currentbrick.gemology.Gemology;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PoofPayload() implements CustomPacketPayload {

    public static final Type<PoofPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Gemology.MODID, "poof_gem"));

    public static final StreamCodec<ByteBuf, PoofPayload> STREAM_CODEC =
            StreamCodec.unit(new PoofPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}