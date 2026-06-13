package me.itzme1on.alcocraftplus.client.gui;

import dev.architectury.hooks.client.fluid.ClientFluidStackHooks;
import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import me.itzme1on.alcocraftplus.core.utils.ColorUtil;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.Sheets;
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
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);

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
    protected void containerTick() {
        super.containerTick();

        if (menu.isCrafting()) {
            bubbleAnimationTimer = (bubbleAnimationTimer + 1) % BUBBLE_ANIM_DURATION_TICKS;
        } else {
            bubbleAnimationTimer = 0;
        }
    }

    private void renderProgressBars(GuiGraphicsExtractor guiGraphics, int x, int y) {
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
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        guiGraphics.text(font, title, titleLabelX, titleLabelY, 0xFF000000 | ColorUtil.getColorFromRGB(215, 171, 121));
        guiGraphics.text(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0xFF000000 | ColorUtil.getColorFromRGB(64, 64, 64), false);

        renderTimer(guiGraphics);
    }

    private void renderTimer(GuiGraphicsExtractor guiGraphics) {
        int colorShadow = 0xFF000000 | ColorUtil.getColorFromRGB(97, 69, 36);
        int colorText = 0xFF000000 | ColorUtil.getColorFromRGB(215, 171, 121);

        int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();

        if (!menu.isCrafting()) {
            guiGraphics.text(font, "00:00", 143, 47, colorShadow);
            guiGraphics.text(font, "00:00", 143, 47, colorText);
        } else {
            if (maxProgress > 0) {
                int remainingProgress = maxProgress - progress;
                int timeInSeconds = remainingProgress / 20;

                String formattedTime = formatTime(timeInSeconds);

                guiGraphics.text(font, formattedTime, 143, 47, colorShadow);
                guiGraphics.text(font, formattedTime, 143, 47, colorText);
            } else {
                guiGraphics.text(font, "00:00", 143, 47, colorShadow);
                guiGraphics.text(font, "00:00", 143, 47, colorText);
            }
        }
    }

    private void renderFluid(GuiGraphicsExtractor guiGraphics, int x, int y) {
        int beerLevel = menu.getBeerLevel();
        int beerType = menu.getBeerType();
        int waterLevel = menu.getWaterLevel();

        int maxWaterLevel = KegEntity.MAX_WATER_LEVEL;

        if (beerLevel > 0) {
            int color = getBeerColor(beerType);
            renderFluidArea(guiGraphics, x, y, beerLevel, maxWaterLevel, color);
        }

        if (waterLevel > 0 && beerLevel < maxWaterLevel) {
            int color = getGuiFluidColor();
            renderFluidArea(guiGraphics, x, y, waterLevel, maxWaterLevel, color);
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

    private void renderFluidArea(GuiGraphicsExtractor guiGraphics, int x, int y, int fluidLevel, int maxFluidLevel, int color) {
        final int tile = 16;
        final int columns = 9;

        final int argb = (0xFF << 24) | (color & 0xFFFFFF);

        int denom = Math.max(1, maxFluidLevel / 4);
        int rows = Math.min(4, (int) Math.ceil((double) fluidLevel / (double) denom));

        TextureAtlasSprite sprite = waterStillSprite();

        for (int row = 0; row < rows; row++) {
            int rowY = y + tile * (3 - row);
            for (int col = 0; col < columns; col++) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x + tile * col, rowY, tile, tile, argb);
            }
        }
    }

    private static TextureAtlasSprite waterStillSprite() {
        return Minecraft.getInstance().getAtlasManager()
                .get(Sheets.BLOCKS_MAPPER.defaultNamespaceApply("water_still"));
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
