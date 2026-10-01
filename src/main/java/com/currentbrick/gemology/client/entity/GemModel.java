package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.entity.EntityGem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class GemModel extends GeoModel<EntityGem> {

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        return getGemResource(((GemRenderState) state).gemId, "entity", false);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        return getGemResource(((GemRenderState) state).gemId, "textures/entity", true);
    }

    @Override
    public Identifier getAnimationResource(EntityGem animatable) {
        return getGemResource(animatable.getGemId(), "animations", false);
    }

    private Identifier getGemResource(Identifier gemId, String folder, boolean texture) {
        if (gemId == null) {
            return Identifier.fromNamespaceAndPath(
                    "gemology",
                    "geo/missing"
            );
        }

        if (texture) {
            return Identifier.fromNamespaceAndPath(
                    gemId.getNamespace(),
                    folder + "/" + gemId.getPath() + "/" + gemId.getPath() + ".png"
            );
        }
        return Identifier.fromNamespaceAndPath(
                gemId.getNamespace(),
                folder + "/" + gemId.getPath()
        );
    }

    public Identifier getSkinTexture(GemRenderState state) {
        return state.skinTexture;
    }


}