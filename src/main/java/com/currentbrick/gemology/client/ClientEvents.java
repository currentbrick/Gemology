package com.currentbrick.gemology.client;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.client.entity.FusionRenderer;
import com.currentbrick.gemology.client.entity.GemRenderer;
import com.currentbrick.gemology.init.ModEntities;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = Gemology.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Gemology.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GEM.get(), GemRenderer::new);
        event.registerEntityRenderer(ModEntities.FUSION.get(), FusionRenderer::new);
    }
}