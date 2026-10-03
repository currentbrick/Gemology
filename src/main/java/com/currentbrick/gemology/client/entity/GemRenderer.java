package com.currentbrick.gemology.client.entity;

import com.currentbrick.gemology.client.entity.layer.*;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemVisualVariant;
import com.currentbrick.gemology.entity.gem.palette.GemPaletteGenerator;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.List;

public class GemRenderer extends GeoEntityRenderer<EntityGem, GemRenderState> {

    private final EquipmentLayerRenderer equipmentRenderer;
    private final HumanoidModel<?> armorModel;
    private final ItemModelResolver itemModelResolver;


    public GemRenderer(EntityRendererProvider.Context context) {
        super(context, new GemModel());

        this.equipmentRenderer = context.getEquipmentRenderer();

        this.itemModelResolver = context.getItemModelResolver();

        ModelPart modelPart = context.bakeLayer(ModelLayers.PLAYER);

        this.armorModel = new HumanoidModel<>(modelPart);

        this.withRenderLayer(new SkinLayer(this));
        this.withRenderLayer(new HairLayer(this));
        this.withRenderLayer(new GemLayer(this));
        this.withRenderLayer(new OutfitLayer(this));
        this.withRenderLayer(new FaceLayer(this));
        this.withRenderLayer(new GemEquipmentLayer(this, context));
        this.withRenderLayer(new GemHeldItemLayer(this, this.itemModelResolver));
    }

    @Override
    public void extractRenderState(EntityGem entity, GemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);

        state.gemId = entity.getGemId();

        state.headEquipment = entity.getItemBySlot(EquipmentSlot.HEAD);
        state.chestEquipment = entity.getItemBySlot(EquipmentSlot.CHEST);
        state.legsEquipment = entity.getItemBySlot(EquipmentSlot.LEGS);
        state.feetEquipment = entity.getItemBySlot(EquipmentSlot.FEET);
        state.mainHandItem = entity.getMainHandItem();

        itemModelResolver.updateForLiving(
                state.mainHandItemState,
                state.mainHandItem,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                entity
        );
    }

    @Override
    public void addRenderData(EntityGem entity, Void relatedObject, GemRenderState state, float partialTick) {
        super.addRenderData(entity, relatedObject, state, partialTick);
        if (!entity.arePalettesLoaded()) {
            entity.loadPalettes(Minecraft.getInstance().getResourceManager());
        }

        Identifier gemId = entity.getGemId();

        if (gemId == null) {
            return;
        }
        GemVisualVariant visualVariant = entity.getVisualVariant();

        state.skinTexture = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/"+gemId.getPath()+"/skin_"+visualVariant.skin()+".png");
        state.hairTexture = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/"+gemId.getPath()+"/hair_"+visualVariant.hair()+".png");
        state.outfitTexture = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/"+gemId.getPath()+"/outfit_"+visualVariant.outfit()+".png");
        state.gemTexture = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/"+gemId.getPath()+"/gemstones/gem_"+visualVariant.gem()+".png");
        state.faceTexture = Identifier.fromNamespaceAndPath(gemId.getNamespace(), "textures/entity/"+gemId.getPath()+"/"+gemId.getPath()+".png");

        state.skinColour = entity.getPaletteColour(GemPaletteGenerator.PaletteType.SKIN);
        state.hairColour = entity.getPaletteColour(GemPaletteGenerator.PaletteType.HAIR);
        state.gemColour = entity.getPaletteColour(GemPaletteGenerator.PaletteType.GEM);
        state.outfitColour = entity.getPaletteColour(GemPaletteGenerator.PaletteType.OUTFIT);
        state.markingsColour = entity.getPaletteColour(GemPaletteGenerator.PaletteType.MARKINGS);
    }

    @Override
    public GemRenderState createRenderState(EntityGem entity, Void relatedObject) {
        return new GemRenderState();
    }

    @Override
    protected Component getNameTag(EntityGem entity) {
        return entity.getDisplayName();
    }

    @Override
    public boolean shouldShowName(EntityGem entity, double distance) {
        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.crosshairPickEntity == entity;
    }
}