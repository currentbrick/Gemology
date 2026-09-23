package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entities.EntityFusion;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class FusionModel extends GeoModel<EntityFusion> {

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath(
                "gemology",
                "entity/fusion"
        );
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath(
                "gemology",
                "textures/entity/fusion/fusion.png"
        );
    }

    @Override
    public Identifier getAnimationResource(EntityFusion animatable) {
        return Identifier.fromNamespaceAndPath(
                "gemology",
                "animations/fusion"
        );
    }
}