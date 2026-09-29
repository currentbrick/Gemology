package com.currentbrick.gemology.client.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

public record GemVariantProperty() implements SelectItemModelProperty<Integer> {

    public static final SelectItemModelProperty.Type<GemVariantProperty, Integer> TYPE =
            SelectItemModelProperty.Type.create(
                    MapCodec.unit(new GemVariantProperty()),
                    com.mojang.serialization.Codec.INT
            );

    @Override
    public Integer get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            return -1;
        }

        return customData.copyTag()
                .getInt("Variant")
                .orElse(-1);
    }

    @Override
    public Codec<Integer> valueCodec() {
        return Codec.INT;
    }

    @Override
    public SelectItemModelProperty.Type<GemVariantProperty, Integer> type() {
        return TYPE;
    }
}