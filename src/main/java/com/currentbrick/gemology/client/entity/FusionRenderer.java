package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.client.entity.layer.*;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteGenerator;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class FusionRenderer extends GeoEntityRenderer<EntityFusion, FusionRenderState> {

    public FusionRenderer(EntityRendererProvider.Context context) {
        super(context, new FusionModel());

        this.withRenderLayer(new FusionSkinLayer(this));
        this.withRenderLayer(new FusionHairLayer(this));
        this.withRenderLayer(new EyeLayer(this));
        this.withRenderLayer(new IrisLayer(this));
        this.withRenderLayer(new FusionOutfitLayer(this));
        this.withRenderLayer(new FusionGemLayer(this));
    }

    @Override
    public FusionRenderState createRenderState(EntityFusion entity, Void relatedObject) {
        return new FusionRenderState();
    }

    @Override
    public void extractRenderState(EntityFusion entity, FusionRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        GemDimensions dimensions = entity.getFusionDimensions();

        if (dimensions != null) {
            state.modelWidth = dimensions.getWidth();
            state.modelHeight = dimensions.getHeight();
        }

        state.gem1Id = entity.getGem1ID();
        state.gem2Id = entity.getGem2ID();

        state.gem1InstanceId = entity.getGem1InstanceId();
        state.gem2InstanceId = entity.getGem2InstanceId();

        state.gem1Variant = entity.getGem1Variant();
        state.gem2Variant = entity.getGem2Variant();
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<FusionRenderState> renderPassInfo, float widthScale, float heightScale) {
        FusionRenderState state = renderPassInfo.renderState();

        float scaleX;
        float scaleY;

        if (state.gem1Id != null && state.gem1Id.equals(state.gem2Id)) {
                scaleX = 1.4F;
                scaleY = 1.4F;
        } else {
            scaleX = state.modelWidth;
            scaleY = state.modelHeight / 2;
        }

        System.out.println(
                "SCALE: " + scaleX + ", " + scaleY +
                        " | IDS: " + state.gem1Id + ", " + state.gem2Id
        );

        super.scaleModelForRender(renderPassInfo, widthScale * scaleX, heightScale * scaleY);
    }

    @Override
    public void addRenderData(EntityFusion entity, Void relatedObject, FusionRenderState state, float partialTick) {
        state.gem1Id = entity.getGem1ID();
        state.gem2Id = entity.getGem2ID();
        state.gem1InstanceId = entity.getGem1InstanceId();
        state.gem2InstanceId = entity.getGem2InstanceId();
        state.gem1Variant = entity.getGem1Variant();
        state.gem2Variant = entity.getGem2Variant();

        System.out.println(
                "ADD RENDER DATA: " +
                        state.gem1Id + " | " +
                        state.gem1InstanceId + " | " +
                        state.gem1Variant
        );
        super.addRenderData(entity, relatedObject, state, partialTick);

        state.skinTexture = Identifier.fromNamespaceAndPath(Gemology.MODID, "textures/entity/fusion/skin_0.png");
        state.hairTexture = Identifier.fromNamespaceAndPath(Gemology.MODID, "textures/entity/fusion/hair_0.png");
        state.outfitTexture = Identifier.fromNamespaceAndPath(Gemology.MODID, "textures/entity/fusion/outfits/outfit_0.png");
        state.eyeTexture = Identifier.fromNamespaceAndPath(Gemology.MODID, "textures/entity/fusion/eyes/eye_"+entity.getVisualVariant().eyes()+".png");
        state.irisTexture = Identifier.fromNamespaceAndPath(Gemology.MODID, "textures/entity/fusion/eyes/iris_"+entity.getVisualVariant().eyes()+".png");

        Identifier gem1Id = state.gem1Id;
        Identifier gem2Id = state.gem2Id;

        System.out.println("STATE GEM 1 ID: " + state.gem1Id);
        System.out.println("STATE GEM 1 INSTANCE: " + state.gem1InstanceId);
        System.out.println("STATE GEM 1 VARIANT: " + state.gem1Variant);


        if (gem1Id != null) {
            GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gem1Id);
            System.out.println("DEFINITION: " + definition);
            if (definition != null) {
                int variant = EntityGem.getVisualVariant(state.gem1InstanceId, definition.getGemVariants(), "gem");

                state.gemTexture1 = Identifier.fromNamespaceAndPath(gem1Id.getNamespace(), "textures/entity/fusion/gemstones/gem_" + variant + ".png");
            }
        }

        if (gem2Id != null) {
            GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(gem2Id);
            if (definition != null) {
                int variant = EntityGem.getVisualVariant(state.gem2InstanceId, definition.getGemVariants(), "gem");

                state.gemTexture2 = Identifier.fromNamespaceAndPath(gem2Id.getNamespace(), "textures/entity/fusion/gemstones/gem_" + variant + ".png");
            }
        }

        System.out.println("GEM 1 TEXTURE: " + state.gemTexture1);
        System.out.println("GEM 2 TEXTURE: " + state.gemTexture2);

        state.skinColour = entity.getCombinedPaletteColour(GemPaletteGenerator.PaletteType.SKIN);
        state.hairColour = entity.getCombinedPaletteColour(GemPaletteGenerator.PaletteType.HAIR);
        state.gemColour = entity.getCombinedPaletteColour(GemPaletteGenerator.PaletteType.GEM);
    }

    @Override
    protected Component getNameTag(EntityFusion entity) {
        return entity.getDisplayName();
    }

    @Override
    public boolean shouldShowName(EntityFusion entity, double distance) {
        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.crosshairPickEntity == entity;
    }
}