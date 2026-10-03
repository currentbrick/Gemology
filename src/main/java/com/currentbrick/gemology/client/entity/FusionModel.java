package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entity.EntityFusion;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class FusionModel extends GeoModel<EntityFusion> {

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        FusionRenderState fusionState = (FusionRenderState) state;
        if (fusionState.gem1 != null && fusionState.gem1.equals(fusionState.gem2)) {

            return Identifier.fromNamespaceAndPath(
                    "gemology",
                    "entity/" + fusionState.gem1.getPath()
            );
        }

        return Identifier.fromNamespaceAndPath(
                "gemology",
                "entity/fusion"
        );
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        FusionRenderState fusionState = (FusionRenderState) state;

        if (fusionState.gem1 != null
                && fusionState.gem1.equals(fusionState.gem2)) {

            return Identifier.fromNamespaceAndPath(
                    "gemology",
                    "textures/entity/" + fusionState.gem1.getPath()
                            + "/" + /*fusionState.gem1.getPath() + */"skin_0.png"
            );
        }

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