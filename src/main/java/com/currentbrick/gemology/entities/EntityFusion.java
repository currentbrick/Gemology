package com.currentbrick.gemology.entities;

import com.currentbrick.gemology.init.ModEntities;
import com.currentbrick.gemology.items.FusionItem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityFusion extends Monster implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private CompoundTag firstGemData;
    private CompoundTag secondGemData;

    public EntityFusion(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public void setComponents(CompoundTag first, CompoundTag second) {
        this.firstGemData = first;
        this.secondGemData = second;
    }

    public CompoundTag getFirstGemData() {
        return firstGemData;
    }

    public CompoundTag getSecondGemData() {
        return secondGemData;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        if (level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = player.getItemInHand(hand);

        if (!(stack.getItem() instanceof FusionItem)) {
            return InteractionResult.PASS;
        }

        if (firstGemData == null || secondGemData == null) {
            return InteractionResult.PASS;
        }

        double offset = 1;

        Vec3 direction = player.getLookAngle();
        direction = new Vec3(direction.x, 0, direction.z).normalize();
        Vec3 side = new Vec3(-direction.z, 0, direction.x).normalize();

        spawnGem(firstGemData, (-side.x * offset), 0, (-side.z * offset));
        spawnGem(secondGemData, (side.x * offset), 0, (side.z * offset));

        discard();

        return InteractionResult.SUCCESS;
    }

    private void spawnGem(CompoundTag gemData, double offsetX, double offsetY, double offsetZ) {
        EntityGem gem = ModEntities.GEM.get().create(level(), EntitySpawnReason.SPAWN_ITEM_USE);

        if (gem == null) {
            return;
        }

        gem.applyGemData(gemData);

        gem.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);

        level().addFreshEntity(gem);
    }
}
