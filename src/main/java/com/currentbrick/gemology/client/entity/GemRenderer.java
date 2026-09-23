package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entities.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GemRenderer extends GeoEntityRenderer<EntityGem, GemRenderState> {

    public GemRenderer(EntityRendererProvider.Context context) {
        super(context, new GemModel());
    }

    @Override
    public void extractRenderState(EntityGem entity, GemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.gemId = entity.getGemId();
    }

    @Override
    public GemRenderState createRenderState(EntityGem entity, Void relatedObject) {
        return new GemRenderState();
    }
}