package com.currentbrick.gemology.client.screen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.IncubatorContainer;
import com.currentbrick.gemology.network.IncubatePayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class IncubatorScreen extends AbstractContainerScreen<IncubatorContainer> {

    private static final int GUI_WIDTH = 208;
    private static final int GUI_HEIGHT = 224;

    private static final Identifier INCUBATOR_TEXTURE = Identifier.fromNamespaceAndPath("gemology", "textures/gui/incubator.png");
    private static final Identifier INCUBATOR_PROGRESS_TEXTURE = Identifier.fromNamespaceAndPath("gemology", "textures/gui/incubator_progress.png");

    public IncubatorScreen(IncubatorContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, INCUBATOR_TEXTURE, leftPos, topPos, 0, 0, GUI_WIDTH, GUI_HEIGHT, GUI_WIDTH, GUI_HEIGHT);

        int progress = menu.getIncubationProgress();
        int time = menu.getIncubationTime();

        if (time > 0) {
            int barWidth = (int) (37.0F * progress / time);
            barWidth = Math.clamp(barWidth, 0, 37);

            if (barWidth > 0) {
                graphics.blit(RenderPipelines.GUI_TEXTURED, INCUBATOR_PROGRESS_TEXTURE, leftPos + 154, topPos + 93, 0, 0, barWidth, 9, 37, 9);
            }
        }

        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {

        if (event.button() == 1) {
            double x = event.x() - leftPos;
            double y = event.y() - topPos;


            if (x >= 148 && x < 197 && y >= 78 && y < 88) {

                ClientPacketDistributor.sendToServer(
                        new IncubatePayload(menu.getBlockPos())
                );

                return true;
            }
        }

        return super.mouseClicked(event, doubleClick);
    }
}