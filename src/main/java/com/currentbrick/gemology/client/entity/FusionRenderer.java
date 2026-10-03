package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

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

        state.gem1 = entity.getGem1ID();
        state.gem2 = entity.getGem2ID();
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<FusionRenderState> renderPassInfo, float widthScale, float heightScale) {
        FusionRenderState state = renderPassInfo.renderState();

        float scaleX;
        float scaleY;

        if (state.gem1 != null && state.gem1.equals(state.gem2)) {
                scaleX = 1.4F;
                scaleY = 1.4F;
        } else {
            scaleX = state.modelWidth;
            scaleY = state.modelHeight / 2.0F;
        }

        System.out.println("SCALE: " + scaleX + ", " + scaleY + " | IDS: " + state.gem1 + ", " + state.gem2);

        super.scaleModelForRender(renderPassInfo, widthScale * scaleX, heightScale * scaleY);
    }

    @Override
    public void addRenderData(EntityFusion entity, Void relatedObject, FusionRenderState state, float partialTick) {
        super.addRenderData(entity, relatedObject, state, partialTick);

        //state.skinTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/skin_0.png");
        //state.hairTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/hair_0.png");
        //state.outfitTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/outfit_0.png");
        //state.gemTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/gemstones/gem_0.png");
    }
}