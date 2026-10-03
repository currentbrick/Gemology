package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.entity.gem.abilities.AbilityTypeRegistry;
import com.currentbrick.gemology.entity.gem.abilities.types.EffectAbility;
import com.currentbrick.gemology.entity.gem.abilities.types.MeleeAbility;
import com.currentbrick.gemology.entity.gem.abilities.types.RangedAbility;
import net.minecraft.resources.Identifier;

public class ModAbilities {

    public static void register() {
        AbilityTypeRegistry.register(Identifier.fromNamespaceAndPath(Gemology.MODID, "melee"), MeleeAbility::new);
        AbilityTypeRegistry.register(Identifier.fromNamespaceAndPath(Gemology.MODID, "ranged"), RangedAbility::new);
        AbilityTypeRegistry.register(Identifier.fromNamespaceAndPath(Gemology.MODID, "effect"), EffectAbility::new);
    }
}
