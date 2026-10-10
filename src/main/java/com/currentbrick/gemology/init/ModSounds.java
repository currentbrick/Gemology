package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Gemology.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> POOF = SOUNDS.register("poof", () ->
                    SoundEvent.createFixedRangeEvent(Identifier.fromNamespaceAndPath(Gemology.MODID, "poof"), 16.0F)
    );
}