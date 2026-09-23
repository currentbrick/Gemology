package com.currentbrick.gemology.entities;

import com.currentbrick.gemology.Gemology;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class EntityGem extends Monster {

    public EntityGem(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    private Identifier gemId;


    public void setGemId(Identifier gemId) {
        this.gemId = gemId;
    }

    public Identifier getGemId() {
        return gemId;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.getString("GemType").ifPresent(value -> {
            Gemology.LOGGER.info("GemType loaded: {}", value);
            gemId = Identifier.parse(value);
        });
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        Gemology.LOGGER.info("Saving GemType: {}", gemId);
        if (gemId != null) {
            output.putString("GemType", gemId.toString());
        }
    }
}
