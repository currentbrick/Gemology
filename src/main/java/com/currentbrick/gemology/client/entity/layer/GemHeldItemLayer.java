package com.currentbrick.gemology.client.entity.layer;

import com.currentbrick.gemology.client.entity.GemRenderState;
import com.currentbrick.gemology.entity.EntityGem;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GemHeldItemLayer extends GeoRenderLayer<EntityGem, Void, GemRenderState> {

    private final ItemModelResolver itemModelResolver;

    public GemHeldItemLayer(GeoEntityRenderer<EntityGem, GemRenderState> renderer, ItemModelResolver itemModelResolver) {
        super(renderer);
        this.itemModelResolver = itemModelResolver;
    }

    @Override
    public void submitRenderTask(RenderPassInfo<GemRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        ItemStackRenderState itemState =
                renderPassInfo.renderState().mainHandItemState;

        if (itemState.isEmpty()) {
            return;
        }

        renderPassInfo.model().getBone("right_arm").ifPresent(bone -> {
            PoseStack poseStack = renderPassInfo.poseStack();

            poseStack.pushPose();

            RenderUtil.transformToBone(poseStack, bone);

            poseStack.translate(
                    0.0F,
                    0.5F,
                    0.0F
            );

            poseStack.scale(
                    0.5F,
                    0.5F,
                    0.5F
            );

            itemState.submit(
                    poseStack,
                    renderTasks,
                    renderPassInfo.packedLight(),
                    renderPassInfo.packedOverlay(),
                    0
            );

            poseStack.popPose();
        });
    }
}