package com.currentbrick.gemology.client.entity.layer;

import com.currentbrick.gemology.client.entity.GemRenderState;
import com.currentbrick.gemology.entity.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.builtin.ItemArmorGeoLayer;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import java.util.List;

public class GemEquipmentLayer
        extends ItemArmorGeoLayer<EntityGem, Void, GemRenderState> {

    private static final List<RenderData> BONES = List.of(
            RenderData.head("head"),
            RenderData.body("body"),
            RenderData.leftArm("left_arm"),
            RenderData.rightArm("right_arm"),
            RenderData.leftLeg("left_leg"),
            RenderData.rightLeg("right_leg")
    );

    public GemEquipmentLayer(GeoEntityRenderer<EntityGem, GemRenderState> renderer, EntityRendererProvider.Context context) {
        super(renderer, context);
    }

    @Override
    protected List<RenderData> getRelevantBones(RenderPassInfo<GemRenderState> renderPassInfo) {
        return BONES;
    }
}