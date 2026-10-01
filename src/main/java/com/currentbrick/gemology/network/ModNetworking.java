package com.currentbrick.gemology.network;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.blockentity.IncubatorBE;
import com.currentbrick.gemology.container.GemUIContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "gemology")
public class ModNetworking {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                SetGemTabPayload.TYPE,
                SetGemTabPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        if (context.player().containerMenu instanceof GemUIContainer container) {
                            container.setSelectedTab(payload.tab());
                        }
                    });
                }
        );

        registrar.playToServer(
                IncubatePayload.TYPE,
                IncubatePayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        Gemology.LOGGER.info("INCUBATE PACKET RECEIVED");
                        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.pos());

                        if (blockEntity instanceof IncubatorBE incubator) {
                            incubator.startIncubation();
                        } else return;

                    });
                }
        );
    }
}