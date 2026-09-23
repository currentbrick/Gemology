package com.currentbrick.gemology.client;

import com.currentbrick.gemology.entities.EntityGem;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class GemRenderer extends EntityRenderer<EntityGem, EntityRenderState> {

    public GemRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}