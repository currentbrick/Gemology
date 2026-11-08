package com.currentbrick.gemology.init;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

public class ModTrimMaterials {

    public static final ResourceKey<TrimMaterial> PRISMATIC = ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath("gemology", "prismatic"));

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        context.register(
                PRISMATIC, new TrimMaterial(
                        Identifier.fromNamespaceAndPath("gemology", "trim/prismatic"),
                        Component.translatable("trim_material.gemology.prismatic")
                )
        );
    }
}