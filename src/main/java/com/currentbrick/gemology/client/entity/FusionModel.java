package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entity.EntityFusion;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class FusionModel extends GeoModel<EntityFusion> {

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        FusionRenderState fusionState = (FusionRenderState) state;

        if (fusionState.gem1Id != null && fusionState.gem1Id.equals(fusionState.gem2Id)) {

            return Identifier.fromNamespaceAndPath("gemology", "entity/" + fusionState.gem1Id.getPath());
        }

        return Identifier.fromNamespaceAndPath("gemology", "entity/fusion");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        FusionRenderState fusionState = (FusionRenderState) state;

        if (fusionState.gem1Id != null
                && fusionState.gem1Id.equals(fusionState.gem2Id)) {

            return Identifier.fromNamespaceAndPath(fusionState.gem1Id.getNamespace(), "textures/entity/" + fusionState.gem1Id.getPath() + "/skin_0.png");
        }

        return Identifier.fromNamespaceAndPath("gemology", "textures/entity/fusion/fusion.png"
        );
    }

    @Override
    public Identifier getAnimationResource(EntityFusion animatable) {
        return Identifier.fromNamespaceAndPath("gemology", "entity/fusion");
    }
}