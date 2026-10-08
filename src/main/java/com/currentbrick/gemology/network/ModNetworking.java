package com.currentbrick.gemology.network;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.blockentity.IncubatorBE;
import com.currentbrick.gemology.container.FusionUIContainer;
import com.currentbrick.gemology.container.GemUIContainer;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
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
                        } else if (context.player().containerMenu instanceof FusionUIContainer container) {
                            container.setSelectedTab(payload.tab());
                        }
                    });
                }
        );

        registrar.playToServer(
                PoofPayload.TYPE,
                PoofPayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        if (context.player().containerMenu instanceof GemUIContainer container) {
                            EntityGem gem = container.gem;

                            if (gem == null || !gem.isAlive()) {
                                return;
                            }

                            gem.poof(context.player());
                        }
                    });
                }
        );

        registrar.playToServer(
                UnfusePayload.TYPE,
                UnfusePayload.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        if (context.player().containerMenu instanceof FusionUIContainer container) {
                            EntityFusion fusion = container.fusion;

                            if (fusion == null || !fusion.isAlive()) {
                                return;
                            }

                            fusion.unfuse(context.player());
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