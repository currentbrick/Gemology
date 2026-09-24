package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.client.entity.layer.GemLayer;
import com.currentbrick.gemology.client.entity.layer.HairLayer;
import com.currentbrick.gemology.client.entity.layer.OutfitLayer;
import com.currentbrick.gemology.client.entity.layer.SkinLayer;
import com.currentbrick.gemology.entities.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

import java.util.List;

public class GemRenderer extends GeoEntityRenderer<EntityGem, GemRenderState> {

    public GemRenderer(EntityRendererProvider.Context context) {
        super(context, new GemModel());
    }

    @Override
    public List<GeoRenderLayer<EntityGem, Void, GemRenderState>> getRenderLayers() {
        return List.of(
                new SkinLayer(this),
                new HairLayer(this),
                new GemLayer(this),
                new OutfitLayer(this)
        );
    }

    @Override
    public void extractRenderState(EntityGem entity, GemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.gemId = entity.getGemId();
    }

    @Override
    public void addRenderData(EntityGem entity, Void relatedObject, GemRenderState state, float partialTick) {
        super.addRenderData(entity, relatedObject, state, partialTick);

        state.skinTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/skin_0.png");
        state.hairTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/hair_0.png");
        state.outfitTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/outfit_0.png");
        state.gemTexture = Identifier.fromNamespaceAndPath(entity.getGemId().getNamespace(), "textures/entity/"+entity.getGemId().getPath()+"/gemstones/gem_0.png");
    }

    @Override
    public GemRenderState createRenderState(EntityGem entity, Void relatedObject) {
        return new GemRenderState();
    }
}