package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class FusionModel extends GeoModel<EntityFusion> {

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        FusionRenderState fusionState = (FusionRenderState) state;

        Identifier type1 = getFusionTypeId(fusionState.gem1Id);
        Identifier type2 = getFusionTypeId(fusionState.gem2Id);

        if (type1 != null && type1.equals(type2)) {
            return Identifier.fromNamespaceAndPath(
                    type1.getNamespace(),
                    "entity/" + type1.getPath()
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

        Identifier type1 = getFusionTypeId(fusionState.gem1Id);
        Identifier type2 = getFusionTypeId(fusionState.gem2Id);

        if (type1 != null && type1.equals(type2)) {
            return Identifier.fromNamespaceAndPath(
                    type1.getNamespace(),
                    "textures/entity/" + type1.getPath() + "/skin_0.png"
            );
        }

        return Identifier.fromNamespaceAndPath(
                "gemology",
                "textures/entity/fusion/fusion.png"
        );
    }

    @Override
    public Identifier getAnimationResource(EntityFusion animatable) {
        return Identifier.fromNamespaceAndPath("gemology", "entity/fusion");
    }

    private Identifier getFusionTypeId(Identifier gemId) {
        if (gemId == null) {
            Gemology.LOGGER.warn("Fusion model received a null gem ID");
            return null;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gemId);

        if (definition == null) {
            Gemology.LOGGER.warn(
                    "No gem definition found for {} when resolving fusion type",
                    gemId
            );
            return gemId;
        }

        Identifier fusionType = definition.getFusionTypeId();

        return fusionType;
    }
}