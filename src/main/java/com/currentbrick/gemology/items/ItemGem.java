package com.currentbrick.gemology.items;

import com.currentbrick.gemology.entities.EntityGem;
import com.currentbrick.gemology.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class ItemGem extends Item {
    private static final int REFORMATION_TIME = 40;

    private final Identifier gemId;

    public ItemGem(Properties properties, Identifier gemId) {
        super(properties.stacksTo(1).fireResistant());
        this.gemId = gemId;
    }

    public Identifier getGemId() {
        return gemId;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide()) {
            EntityGem gem = createGem(level, context.getItemInHand());
            if (gem != null) {
                BlockPos pos = context.getClickedPos();
                gem.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                level.addFreshEntity(gem);
                Player player = context.getPlayer();
                if (player == null || !player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }


    public EntityGem createGem(Level level, ItemStack stack) {
        EntityGem gem = ModEntities.GEM.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (gem != null) {
            gem.setGemId(gemId);
        }

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();

            tag.getString("Owner").ifPresent(owner -> {
                gem.setOwnerUUID(UUID.fromString(owner));
            });
        }
        return gem;
    }

}