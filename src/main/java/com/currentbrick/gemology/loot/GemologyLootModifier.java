package com.currentbrick.gemology.loot;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.Optional;

public class GemologyLootModifier extends LootModifier {

    public static final MapCodec<GemologyLootModifier> CODEC =
            MapCodec.unit(new GemologyLootModifier());

    private static final ResourceKey<LootTable> GEMOLOGY_TABLE = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath("gemology", "chests/gemology_common"));

    private GemologyLootModifier() {
        super(Optional.empty(), 1000);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Identifier lootTableId = context.getQueriedLootTableId();

        if (!isTargetTable(lootTableId)) {
            return generatedLoot;
        }

        context.getResolver().lookupOrThrow(Registries.LOOT_TABLE).get(GEMOLOGY_TABLE)
                .ifPresent(gemologyTable -> {
                    gemologyTable.value().getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
                });

        return generatedLoot;
    }

    private boolean isTargetTable(Identifier id) {
        return id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/abandoned_mineshaft"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/simple_dungeon"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/desert_pyramid"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/jungle_temple"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/shipwreck_supply"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/buried_treasure"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_corridor"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/nether_bridge"))
                || id.equals(Identifier.fromNamespaceAndPath("minecraft", "chests/woodland_mansion"));
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}