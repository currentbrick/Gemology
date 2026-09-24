package com.currentbrick.gemology.client.entity.layer;

import com.currentbrick.gemology.client.entity.GemRenderState;
import com.currentbrick.gemology.entities.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class HairLayer extends GeoRenderLayer<EntityGem, Void, GemRenderState> {

    public HairLayer(GeoEntityRenderer<EntityGem, GemRenderState> renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<GemRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        Identifier texture = renderPassInfo.renderState().hairTexture;
        if (texture == null) {
            return;
        }
        RenderType renderType = RenderTypes.entityCutout(texture);

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
                                    renderPassInfo.renderColor()
                            )
                    );

                    poseStack.popPose();
                }
        );
    }
}