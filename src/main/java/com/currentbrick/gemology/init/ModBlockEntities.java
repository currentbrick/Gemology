package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.blockentity.IncubatorBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Gemology.MODID);

    public static final Supplier<BlockEntityType<IncubatorBE>> INCUBATOR_BE =
            BLOCK_ENTITIES.register("incubator_be", () -> new BlockEntityType<>(
                    IncubatorBE::new, ModBlocks.INCUBATOR.get()));
}