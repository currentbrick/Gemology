package com.currentbrick.gemology.client.screen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.FusionUIContainer;
import com.currentbrick.gemology.container.GemUIContainer;
import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.network.PoofPayload;
import com.currentbrick.gemology.network.SetGemTabPayload;
import com.currentbrick.gemology.network.UnfusePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FusionUIScreen extends AbstractContainerScreen<FusionUIContainer> {

    private static final int GUI_WIDTH = 208;
    private static final int GUI_HEIGHT = 224;

    private static final int TAB_WIDTH = 32;
    private static final int TAB_HEIGHT = 28;
    private static final int TAB_X_OFFSET = 0;
    private static final int TAB_Y_OFFSET = 4;

    private static final Identifier APPEARANCE_TEXTURE = Identifier.fromNamespaceAndPath("gemology", "textures/gui/gem_appearance.png");
    private static final Identifier INVENTORY_TEXTURE = Identifier.fromNamespaceAndPath("gemology", "textures/gui/fusion_inventory.png");
    private static final Identifier STATS_TEXTURE = Identifier.fromNamespaceAndPath("gemology", "textures/gui/gem_stats.png");

    private static final Identifier APPEARANCE_TAB = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/appearance.png");
    private static final Identifier APPEARANCE_TAB_SELECTED = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/appearance_selected.png");
    private static final Identifier INVENTORY_TAB = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/inventory.png");
    private static final Identifier INVENTORY_TAB_SELECTED = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/inventory_selected.png");
    private static final Identifier STATS_TAB = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/stats.png");
    private static final Identifier STATS_TAB_SELECTED = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/stats_selected.png");
    private static final Identifier POOF_BUTTON = Identifier.fromNamespaceAndPath("gemology", "textures/gui/tabs/poof_l.png");

    public FusionUIScreen(FusionUIContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        Identifier texture = switch (menu.getSelectedTab()) {
            case 0 -> APPEARANCE_TEXTURE;
            case 1 -> INVENTORY_TEXTURE;
            case 2 -> STATS_TEXTURE;
            default -> APPEARANCE_TEXTURE;
        };

        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, leftPos, topPos, 0, 0, 208, 224, 208, 224);

        Identifier[] normalTabs = {
                APPEARANCE_TAB,
                INVENTORY_TAB,
                STATS_TAB
        };

        Identifier[] selectedTabs = {
                APPEARANCE_TAB_SELECTED,
                INVENTORY_TAB_SELECTED,
                STATS_TAB_SELECTED
        };

        for (int i = 0; i < 3; i++) {
            Identifier tabTexture = i == menu.getSelectedTab() ? selectedTabs[i] : normalTabs[i];

            graphics.blit(RenderPipelines.GUI_TEXTURED, tabTexture, leftPos + GUI_WIDTH + TAB_X_OFFSET, topPos + i * 28 + TAB_Y_OFFSET, 0, 0, TAB_WIDTH, TAB_HEIGHT, TAB_WIDTH, TAB_HEIGHT);
        }

        for (int i = 0; i < 3; i++) {
            if (isMouseOverTab(mouseX, mouseY, i)) {
                Component tooltip = switch (i) {
                    case 0 -> Component.literal("Appearance");
                    case 1 -> Component.literal("Inventory");
                    case 2 -> Component.literal("Stats & Abilities");
                    default -> Component.empty();
                };
                graphics.setTooltipForNextFrame(
                        tooltip,
                        mouseX,
                        mouseY
                );
                break;
            }
        }

        if (menu.getSelectedTab() == 0) renderGemPreview(graphics, mouseX, mouseY);

        if (menu.getSelectedTab() == 2) renderStats(graphics);

        int poofX = leftPos - TAB_WIDTH;
        int poofY = topPos + GUI_HEIGHT - TAB_HEIGHT;

        graphics.blit(RenderPipelines.GUI_TEXTURED, POOF_BUTTON, poofX, poofY, 0, 0, TAB_WIDTH, TAB_HEIGHT, TAB_WIDTH, TAB_HEIGHT);

        super.extractContents(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
    }

    private boolean isMouseOverTab(double mouseX, double mouseY, int tab) {
        double tabX = leftPos + GUI_WIDTH + TAB_X_OFFSET;
        double tabY = topPos + tab * 28 + TAB_Y_OFFSET;

        return mouseX >= tabX && mouseX < tabX + TAB_WIDTH && mouseY >= tabY && mouseY < tabY + TAB_HEIGHT;
    }

    private boolean isMouseOverPoof(double mouseX, double mouseY) {
        double poofX = leftPos - TAB_WIDTH;
        double poofY = topPos + GUI_HEIGHT - TAB_HEIGHT;

        return mouseX >= poofX
                && mouseX < poofX + TAB_WIDTH
                && mouseY >= poofY
                && mouseY < poofY + TAB_HEIGHT;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 1) {
            for (int i = 0; i < 3; i++) {
                if (isMouseOverTab(event.x(), event.y(), i)) {
                    ClientPacketDistributor.sendToServer(new SetGemTabPayload(i));
                    return true;
                }
            }

            if (isMouseOverPoof(event.x(), event.y())) {
                ClientPacketDistributor.sendToServer(new UnfusePayload());
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    private void renderGemPreview(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        Minecraft minecraft = Minecraft.getInstance();

        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();

        EntityRenderer<? super EntityFusion, ?> renderer = dispatcher.getRenderer(menu.fusion);

        EntityRenderState renderState = renderer.createRenderState(menu.fusion, minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(true));
        float previewCenterY = topPos + 81.0f;

        float previewCenterX = leftPos + 47.5f;
        float mouseOffsetX = mouseX - previewCenterX;

        float yaw = mouseOffsetX * 0.02f;

        graphics.entity(
                renderState,
                40.0f,
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Quaternionf()
                        .rotateY(-yaw)
                        .rotateX((float) Math.PI),
                null,
                leftPos + 13,
                topPos + 28,
                leftPos + 82,
                topPos + 134
        );
    }

    private void renderStats(GuiGraphicsExtractor graphics) {
        if (menu.fusion == null) {
            return;
        }

        int x = leftPos + 28;
        int y = topPos + 22;

        graphics.text(Minecraft.getInstance().font, "HP: " + (Math.round(menu.fusion.getFusionHealth() * 10) / 10.0f), x, y, 0xFF6E7070, false);


        y += 12;

        graphics.text(Minecraft.getInstance().font, "Attack: " + (Math.round(menu.fusion.getFusionStrength() * 10) / 10.0f), x, y, 0xFF6E7070, false);

        y += 12;

        graphics.text(Minecraft.getInstance().font, "Speed: " + (Math.round(menu.fusion.getFusionSpeed() * 10) / 10.0f), x, y, 0xFF6E7070, false);

        x = leftPos + 120;
        y = topPos + 22;

        for (var ability : menu.fusion.getFusionAbilities()) {
            String abilityName = Arrays.stream(ability.getPath().split("_"))
                    .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                    .collect(Collectors.joining(" "));

            graphics.text(Minecraft.getInstance().font, abilityName, x, y, 0xFF6E7070, false);

            y += 12;
        }
    }
}