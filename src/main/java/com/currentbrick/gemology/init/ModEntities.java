package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entities.EntityGem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Gemology.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityGem>> GEM =
            ENTITY_TYPES.register("gem", () ->
                    EntityType.Builder.of(EntityGem::new, MobCategory.CREATURE)
                            .sized(0.8F, 2.0F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(Gemology.MODID, "gem")))
            );


}
