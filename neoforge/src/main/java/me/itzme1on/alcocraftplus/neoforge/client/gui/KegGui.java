package me.itzme1on.alcocraftplus.neoforge.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.hooks.fluid.FluidStackHooks;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.utils.ColorUtil;
import me.itzme1on.alcocraftplus.fabric.client.gui.handler.KegGuiHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@Environment(EnvType.CLIENT)
public class KegGui extends AbstractContainerScreen<KegGuiHandler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/keg_gui.png");
    private static final ResourceLocation BG = new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/dark_bg.png");

    private static final Fluid WATER_FLUID = Fluids.WATER;

    private static final int[] BUBBLE_LENGTHS = {0, 6, 11, 16, 20, 24, 28};

    private int bubbleAnimationTimer;
    private final int animationDurationTicks = 100;

    public KegGui(KegGuiHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.setShaderTexture(0, BG);
        int x = (width - imageWidth) / 2 + 3;
        int y = (height - imageHeight) / 2 + 3;
        guiGraphics.blit(BG, x, y, 0, 0, imageWidth - 6, imageHeight - 6);

        renderFluid(guiGraphics, x, y);

        RenderSystem.setShaderTexture(0, TEXTURE);

        x = (width - imageWidth) / 2;
        y = (height - imageHeight) / 2 - 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight + 2);

        renderProgressBars(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if (menu.isCrafting(0)) {
            bubbleAnimationTimer++;

            if (bubbleAnimationTimer >= animationDurationTicks) bubbleAnimationTimer = 0;
        } else {
            bubbleAnimationTimer = 0;
        }
    }

    private void renderProgressBars(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting(0)) {
            int bubbleIndex = bubbleAnimationTimer * BUBBLE_LENGTHS.length / animationDurationTicks;

            if (bubbleIndex >= BUBBLE_LENGTHS.length) bubbleIndex = 0;

            int bubbleHeight = getBubbleHeight(bubbleIndex);

            guiGraphics.blit(TEXTURE, x + 150, y + 17 + 28 - bubbleHeight, 176, 28 - bubbleHeight, 11, bubbleHeight);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, ColorUtil.getColorFromRGB(215, 171, 121));
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, ColorUtil.getColorFromRGB(64, 64, 64), false);

        renderTimer(guiGraphics);
    }

    private void renderTimer(GuiGraphics guiGraphics) {
        int colorShadow = ColorUtil.getColorFromRGB(97, 69, 36);
        int colorText = ColorUtil.getColorFromRGB(215, 171, 121);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();

        if (!menu.isCrafting(0)) {
            guiGraphics.drawString(font, "00:00", 143, 47, colorShadow);
            guiGraphics.drawString(font, "00:00", 143, 47, colorText);
        } else {
            if (maxProgress > 0) {
                int remainingProgress = maxProgress - progress;
                int timeInSeconds = (int) ((float) remainingProgress / maxProgress * (maxProgress / 20f));

                String formattedTime = formatTime(timeInSeconds);

                guiGraphics.drawString(font, formattedTime, 143, 47, colorShadow);
                guiGraphics.drawString(font, formattedTime, 143, 47, colorText);
            } else {
                guiGraphics.drawString(font, "00:00", 143, 47, colorShadow);
                guiGraphics.drawString(font, "00:00", 143, 47, colorText);
            }
        }
    }

    private void renderFluid(GuiGraphics guiGraphics, int x, int y) {
        int beerLevel = menu.getBeerLevel();
        int beerType = menu.getBeerType();
        int waterLevel = menu.getWaterLevel();

        int maxWaterLevel = 40;

        TextureAtlasSprite sprite = FluidStackHooks.getStillTexture(fluid);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        if (beerLevel > 0) {
            int color = getBeerColor(beerType);

            renderFluidArea(guiGraphics, x, y, beerLevel, maxWaterLevel, color, sprite);
        }

        if (waterLevel > 0 && beerLevel < maxWaterLevel) {
            int color = FluidStackHooks.getColor(fluid);

            renderFluidArea(guiGraphics, x, y, waterLevel, maxWaterLevel, color, sprite);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderFluidArea(GuiGraphics guiGraphics, int x, int y, int fluidLevel, int maxFluidLevel, int color, TextureAtlasSprite sprite) {
        int textureWidth = 16;
        int textureHeight = 16;

        RenderSystem.setShaderColor(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, 1.0f);

        int numRowsToDraw = (int) Math.ceil((double) fluidLevel / (maxFluidLevel / 4.0));

        numRowsToDraw = Math.min(numRowsToDraw, 4);

        for (int row = 0; row < numRowsToDraw; row++) {
            for (int col = 0; col <= 8; col++) {
                guiGraphics.blit(x + textureWidth * col, y + textureHeight * (3 - row), 0, textureWidth, textureHeight, sprite);
            }
        }
    }

    private int getBeerColor(int beerType) {
        return switch (beerType) {
            case 1 -> ColorUtil.getColorFromRGB(222, 167, 81);
            case 2 -> ColorUtil.getColorFromRGB(201, 125, 31);
            case 3 -> ColorUtil.getColorFromRGB(107, 30, 5);
            case 4 -> ColorUtil.getColorFromRGB(29, 5, 3);
            case 5 -> ColorUtil.getColorFromRGB(170, 14, 1);
            case 6 -> ColorUtil.getColorFromRGB(75, 165, 128);
            case 7 -> ColorUtil.getColorFromRGB(58, 70, 123);
            case 8 -> ColorUtil.getColorFromRGB(143, 181, 246);
            case 9 -> ColorUtil.getColorFromRGB(188, 137, 39);
            case 10 -> ColorUtil.getColorFromRGB(69, 201, 72);
            case 11 -> ColorUtil.getColorFromRGB(142, 102, 141);
            case 12 -> ColorUtil.getColorFromRGB(199, 183, 0);
            default -> ColorUtil.getColorFromRGB(255, 255, 255);
        };
    }

    private String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private int getBubbleHeight(int progress) {
        return BUBBLE_LENGTHS[progress % BUBBLE_LENGTHS.length];
    }
}
