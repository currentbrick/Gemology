package com.currentbrick.gemology.client.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class GemRenderState extends EntityRenderState implements GeoRenderState {
    public Identifier gemId;
    public Identifier skinTexture;
    public Identifier hairTexture;
    public Identifier outfitTexture;
    public Identifier gemTexture;
}