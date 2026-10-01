package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
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

        GemDimensions dimensions = entity.getFusionDimensions();

        if (dimensions != null) {
            state.modelWidth = dimensions.getWidth();
            state.modelHeight = dimensions.getHeight();
        }
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<FusionRenderState> renderPassInfo, float widthScale, float heightScale) {
        FusionRenderState state = renderPassInfo.renderState();

        float scaleX = state.modelWidth / 1.0F;
        float scaleY = state.modelHeight / 2.0F;

        super.scaleModelForRender(renderPassInfo, widthScale * scaleX, heightScale * scaleY);
    }
}