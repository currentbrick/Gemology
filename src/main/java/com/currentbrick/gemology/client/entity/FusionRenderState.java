package com.currentbrick.gemology.client.entity;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

public class FusionRenderState extends EntityRenderState implements GeoRenderState {
    public float modelWidth = 1.0F;
    public float modelHeight = 2.0F;

    public Identifier gem1;
    public Identifier gem2;

    public Identifier skinTexture;
    public Identifier hairTexture;
    public Identifier outfitTexture;
    public Identifier gemTexture;
}