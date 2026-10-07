package com.currentbrick.gemology.client.entity.layer;

import com.currentbrick.gemology.client.entity.FusionRenderState;
import com.currentbrick.gemology.client.entity.GemRenderState;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class IrisLayer extends GeoRenderLayer<EntityFusion, Void, FusionRenderState> {

    public IrisLayer(GeoEntityRenderer<EntityFusion, FusionRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<FusionRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        Identifier texture = renderPassInfo.renderState().irisTexture;
        if (texture == null) {
            return;
        }
        RenderType renderType = RenderTypes.entityCutoutZOffset(texture);

        renderTasks.submitCustomGeometry(renderPassInfo.poseStack(), renderType,
                (pose, vertexConsumer) -> {

                    PoseStack poseStack = renderPassInfo.poseStack();

                    poseStack.pushPose();
                    poseStack.last().set(pose);

                    renderPassInfo.renderPosed(() ->
                            renderPassInfo.model().render(
                                    renderPassInfo,
                                    vertexConsumer,
                                    renderPassInfo.packedLight(),
                                    renderPassInfo.packedOverlay(),
                                    renderPassInfo.renderState().gemColour
                            )
                    );

                    poseStack.popPose();
                }
        );
    }
}