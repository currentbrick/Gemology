package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entities.EntityFusion;
import com.currentbrick.gemology.entities.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class FusionRenderer extends GeoEntityRenderer<EntityFusion, FusionRenderState> {

    public FusionRenderer(EntityRendererProvider.Context context) {
        super(context, new FusionModel());
    }

    @Override
    public FusionRenderState createRenderState(EntityFusion entity, Void relatedObject) {
        return new FusionRenderState();
    }

    @Override
    public void extractRenderState(EntityFusion entity, FusionRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
    }
}