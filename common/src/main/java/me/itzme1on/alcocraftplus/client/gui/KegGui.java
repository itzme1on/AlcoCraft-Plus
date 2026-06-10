package me.itzme1on.alcocraftplus.client.gui;

import dev.architectury.hooks.client.fluid.ClientFluidStackHooks;
import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import me.itzme1on.alcocraftplus.core.utils.ColorUtil;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class KegGui extends AbstractContainerScreen<KegGuiHandler> {
    private static final Identifier TEXTURE = IdentifierUtil.of("textures/gui/keg_gui.png");
    private static final Identifier BG = IdentifierUtil.of("textures/gui/dark_bg.png");

    private static final Fluid DEFAULT_FLUID = Fluids.WATER;

    private static final int[] BUBBLE_LENGTHS = {0, 6, 11, 16, 20, 24, 28};
    private static final int BG_WIDTH = 170;
    private static final int BG_HEIGHT = 162;
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;

    private static final int BACKGROUND_INSET = 3;
    private static final int FLUID_X_OFFSET = 3;
    private static final int FLUID_Y_OFFSET = 3;
    private static final int BUBBLE_X_OFFSET = 150;
    private static final int BUBBLE_Y_OFFSET = 17;
    private static final int BUBBLE_AREA_HEIGHT = 28;

    private static final int BUBBLE_ANIM_DURATION_TICKS = 20;
    private int bubbleAnimationTimer;

    public KegGui(KegGuiHandler menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2 - 2;
        int fluidX = (width - imageWidth) / 2 + FLUID_X_OFFSET;
        int fluidY = (height - imageHeight) / 2 + FLUID_Y_OFFSET;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BG,
                x + BACKGROUND_INSET, y + BACKGROUND_INSET, 0, 0,
                imageWidth - BACKGROUND_INSET * 2, imageHeight - BACKGROUND_INSET * 2,
                BG_WIDTH, BG_HEIGHT
        );

        renderFluid(guiGraphics, fluidX, fluidY);

        guiGraphics.blit(
                RenderPipelines.GUI_TEXTURED, TEXTURE,
                x, y, 0, 0,
                imageWidth, imageHeight + 2,
                TEXTURE_WIDTH, TEXTURE_HEIGHT
        );

        renderProgressBars(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        if (menu.isCrafting()) {
            bubbleAnimationTimer = (bubbleAnimationTimer + 1) % BUBBLE_ANIM_DURATION_TICKS;
        } else {
            bubbleAnimationTimer = 0;
        }
    }

    private void renderProgressBars(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            int bubbleIndex = bubbleAnimationTimer * BUBBLE_LENGTHS.length / BUBBLE_ANIM_DURATION_TICKS;

            if (bubbleIndex >= BUBBLE_LENGTHS.length) bubbleIndex = 0;

            int bubbleHeight = getBubbleHeight(bubbleIndex);

            guiGraphics.blit(
                    RenderPipelines.GUI_TEXTURED, TEXTURE,
                    x + BUBBLE_X_OFFSET, y + BUBBLE_Y_OFFSET + BUBBLE_AREA_HEIGHT - bubbleHeight,
                    176, BUBBLE_AREA_HEIGHT - bubbleHeight,
                    11, bubbleHeight,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT
            );
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 0xFF000000 | ColorUtil.getColorFromRGB(215, 171, 121));
        guiGraphics.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0xFF000000 | ColorUtil.getColorFromRGB(64, 64, 64), false);

        renderTimer(guiGraphics);
    }

    private void renderTimer(GuiGraphics guiGraphics) {
        int colorShadow = 0xFF000000 | ColorUtil.getColorFromRGB(97, 69, 36);
        int colorText = 0xFF000000 | ColorUtil.getColorFromRGB(215, 171, 121);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();

        if (!menu.isCrafting()) {
            guiGraphics.drawString(font, "00:00", 143, 47, colorShadow);
            guiGraphics.drawString(font, "00:00", 143, 47, colorText);
        } else {
            if (maxProgress > 0) {
                int remainingProgress = maxProgress - progress;
                int timeInSeconds = remainingProgress / 20;

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

        int maxWaterLevel = KegEntity.MAX_WATER_LEVEL;

        TextureAtlasSprite sprite = ClientFluidStackHooks.getStillTexture(DEFAULT_FLUID);

        if (beerLevel > 0) {
            int color = getBeerColor(beerType);

            assert sprite != null;

            renderFluidArea(guiGraphics, x, y, beerLevel, maxWaterLevel, color, sprite);
        }

        if (waterLevel > 0 && beerLevel < maxWaterLevel) {
            int color = getGuiFluidColor();

            assert sprite != null;

            renderFluidArea(guiGraphics, x, y, waterLevel, maxWaterLevel, color, sprite);
        }
    }

    private int getGuiFluidColor() {
        if (KegGui.DEFAULT_FLUID == Fluids.WATER || KegGui.DEFAULT_FLUID == Fluids.FLOWING_WATER) {
            return ColorUtil.getColorFromRGB(63, 118, 228);
        }

        int c = ClientFluidStackHooks.getColor(KegGui.DEFAULT_FLUID);
        int rgb = c & 0xFFFFFF;
        if (rgb == 0) return ColorUtil.getColorFromRGB(255, 255, 255);
        return rgb;
    }

    private void renderFluidArea(GuiGraphics guiGraphics, int x, int y, int fluidLevel, int maxFluidLevel, int color, TextureAtlasSprite sprite) {
        final int textureWidth = 16, textureHeight = 16;

        final int argb = (0xFF << 24) | (color & 0xFFFFFF);

        int denom = Math.max(1, maxFluidLevel / 4);
        int rows = Math.min(4, (int) Math.ceil((double) fluidLevel / (double) denom));

        int srcW = sprite.contents().width();
        int srcH = sprite.contents().height();
        float u0 = sprite.getU0(), u1 = sprite.getU1();
        float v0 = sprite.getV0(), v1 = sprite.getV1();

        int atlasW = Math.max(1, Math.round(srcW / (u1 - u0)));
        int atlasH = Math.max(1, Math.round(srcH / (v1 - v0)));
        float uPx = u0 * atlasW;
        float vPx = v0 * atlasH;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= 8; col++) {
                guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        sprite.atlasLocation(),
                        x + textureWidth * col,
                        y + textureHeight * (3 - row),
                        uPx, vPx,
                        textureWidth, textureHeight,
                        atlasW, atlasH,
                        argb
                );
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
            case 12 -> ColorUtil.getColorFromRGB(236, 231, 255);
            default -> ColorUtil.getColorFromRGB(255, 255, 255);
        };
    }

    private String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private int getBubbleHeight(int bubbleIndex) {
        return BUBBLE_LENGTHS[bubbleIndex % BUBBLE_LENGTHS.length];
    }
}
