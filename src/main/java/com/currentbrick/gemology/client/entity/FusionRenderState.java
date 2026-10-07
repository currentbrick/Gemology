package com.currentbrick.gemology.client.entity;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public class FusionRenderState extends EntityRenderState implements GeoRenderState {

    public float modelWidth = 1.0F;
    public float modelHeight = 2.0F;

    // Fusion component data
    public Identifier gem1Id;
    public Identifier gem2Id;

    public UUID gem1InstanceId;
    public UUID gem2InstanceId;

    public int gem1Variant = -1;
    public int gem2Variant = -1;

    public Identifier skinTexture;
    public Identifier hairTexture;
    public Identifier outfitTexture;
    public Identifier gemTexture1;
    public Identifier gemTexture2;
    public Identifier eyeTexture;
    public Identifier irisTexture;

    public int skinColour = 0xFFFFFFFF;
    public int hairColour = 0xFFFFFFFF;
    public int gemColour = 0xFFFFFFFF;
    public int outfitColour = 0xFFFFFFFF;
}