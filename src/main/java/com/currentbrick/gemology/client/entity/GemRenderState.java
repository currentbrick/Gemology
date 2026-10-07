package com.currentbrick.gemology.client.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class GemRenderState extends EntityRenderState implements GeoRenderState {
    public Identifier gemId;
    public Identifier skinTexture;
    public Identifier hairTexture;
    public Identifier outfitTexture;
    public Identifier insigniaTexture;
    public Identifier gemTexture;
    public Identifier faceTexture;
    public int skinColour = 0xFFFFFFFF;
    public int hairColour = 0xFFFFFFFF;
    public int gemColour = 0xFFFFFFFF;
    public int outfitColour = 0xFFFFFFFF;
    public int markingsColour = 0xFFFFFFFF;
    public ItemStack headEquipment = ItemStack.EMPTY;
    public ItemStack chestEquipment = ItemStack.EMPTY;
    public ItemStack legsEquipment = ItemStack.EMPTY;
    public ItemStack feetEquipment = ItemStack.EMPTY;
    public ItemStack mainHandItem = ItemStack.EMPTY;
    public final ItemStackRenderState mainHandItemState = new ItemStackRenderState();
}