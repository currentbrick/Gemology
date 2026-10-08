package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.FusionUIContainer;
import com.currentbrick.gemology.container.GemUIContainer;
import com.currentbrick.gemology.container.IncubatorContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModContainers {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Gemology.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<GemUIContainer>> GEM_UI_CONTAINER =
            MENUS.register("gem_ui", () -> IMenuTypeExtension.create(GemUIContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<FusionUIContainer>> FUSION_UI_CONTAINER =
            MENUS.register("fusion_ui", () -> IMenuTypeExtension.create(FusionUIContainer::new));

    public static final DeferredHolder<MenuType<?>, MenuType<IncubatorContainer>> INCUBATOR_CONTAINER =
            MENUS.register("incubator", () -> IMenuTypeExtension.create(IncubatorContainer::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}