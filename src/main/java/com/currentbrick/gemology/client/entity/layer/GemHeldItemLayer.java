package com.currentbrick.gemology.client.entity.layer;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.client.entity.GemRenderState;
import com.currentbrick.gemology.entity.EntityGem;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;

import java.util.function.BiConsumer;

public class GemHeldItemLayer extends GeoRenderLayer<EntityGem, Void, GemRenderState> {

    public GemHeldItemLayer(GeoEntityRenderer<EntityGem, GemRenderState> renderer, ItemModelResolver itemModelResolver) {
        super(renderer);
    }

    @Override
    public void addPerBoneRender(RenderPassInfo<GemRenderState> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<GemRenderState>> consumer) {
        renderPassInfo.model().getBone("right_arm").ifPresent(bone -> {
            consumer.accept(bone, (info, renderedBone, renderTasks) -> {

                var itemState = info.renderState().mainHandItemState;
                PoseStack poseStack = renderPassInfo.poseStack();

                poseStack.pushPose();

                float yOffset = Gemology.GEM_DEFINITION_MANAGER.get(renderPassInfo.renderState().gemId).getDimensions().getHeight() * -0.3F;

                poseStack.translate(-0.05F, yOffset, 0.0F);

                poseStack.rotateDegrees(Axis.XP, -90.0F);

                itemState.submit(poseStack, renderTasks, renderPassInfo.packedLight(), renderPassInfo.packedOverlay(), 0);

                poseStack.popPose();
            });
        });
    }
}