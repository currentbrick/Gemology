package com.currentbrick.gemology.network;

import com.currentbrick.gemology.Gemology;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record UnfusePayload() implements CustomPacketPayload {

    public static final Type<UnfusePayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Gemology.MODID, "unfuse_fusion"));

    public static final StreamCodec<ByteBuf, UnfusePayload> STREAM_CODEC =
            StreamCodec.unit(new UnfusePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}