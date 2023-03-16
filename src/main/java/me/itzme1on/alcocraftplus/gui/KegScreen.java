package me.itzme1on.alcocraftplus.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.slf4j.Logger;

public class KegScreen extends AbstractContainerScreen<KegMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/keg_gui.png");
    private static final ResourceLocation OVERLAY =
            new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/keg_overlay.png");
    private static final ResourceLocation BG =
            new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/dark_bg.png");

    private static final Fluid fluid = Fluids.WATER;

    private static final int[] BUBBLELENGTHS = new int[]{28, 24, 20, 16, 11, 6, 0};

    private static final Logger LOGGER = LogUtils.getLogger();

    public KegScreen(KegMenu kegMenu, Inventory inventory, Component component) {
        super(kegMenu, inventory, component);
    }

    public static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);
        return rgb;
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, rawColorFromRGB(215, 171, 121));
        this.font.draw(pPoseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
        if (!menu.isCrafting()) {
            this.font.draw(pPoseStack, "00:00", 144, 50, rawColorFromRGB(97, 69, 36));
            this.font.draw(pPoseStack, "00:00", 143, 49, rawColorFromRGB(215, 171, 121));
        } else {
            int time = this.menu.getProgress()/20;
            int minutes = (int) (time / 60);
            int seconds = (int) (time % 60);

            String min = String.valueOf(minutes);
            if (minutes < 10) {
                min = "0" + min;
            }

            String sec =  String.valueOf(seconds);
            if (seconds < 10) {
                sec = "0" + sec;
            }

            this.font.draw(pPoseStack, min + ":" + sec, 144, 50, rawColorFromRGB(97, 69, 36));
            this.font.draw(pPoseStack, min + ":" + sec, 143, 49, rawColorFromRGB(215, 171, 121));
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        //Background
        RenderSystem.setShaderTexture(0, BG);

        int x = (width - imageWidth) / 2 + 3;
        int y = (height - imageHeight) / 2 + 3;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth - 6, imageHeight - 6);


        //Input fluid
        Fluid guiFluid = fluid;
        ResourceLocation location = guiFluid.getAttributes().getStillTexture();
        TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
        int color = guiFluid.getAttributes().getColor();

        int z = this.menu.getWaterLevel();
        x = (width - sprite.getWidth()) / 2 - 80;
        y = (height - sprite.getHeight()) / 2 - 19 - z;

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.setShaderColor(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f,
                (color & 0xFF) / 255f, ((color >> 24) & 0xFF) / 255f);

        for (int j = 0; j <= 3; j++) {
            for (int i = 0; i <= 8; i++) {
                this.blit(pPoseStack, x + 16 * i, y + 16 * j, 0, 16, 16, sprite);
            }
        }

        //Result fluid
        location = guiFluid.getAttributes().getStillTexture();
        sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);

        z = this.menu.getBeerLevel();
        x = (width - sprite.getWidth()) / 2 - 80;
        y = (height - sprite.getHeight()) / 2 - 19 - z;

        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

        if (menu.getBeerType() == 1) {
            RenderSystem.setShaderColor(222f / 255f, 167f / 255f,
                    81f / 255f, 1.0f);
        } else if (menu.getBeerType() == 2) {
            RenderSystem.setShaderColor(201f / 255f, 125f / 255f,
                    31f / 255f, 1.0f);
        } else if (menu.getBeerType() == 3) {
            RenderSystem.setShaderColor(107f / 255f, 30f / 255f,
                    5f / 255f, 1.0f);
        } else if (menu.getBeerType() == 4) {
            RenderSystem.setShaderColor(29f / 255f, 5f / 255f,
                    3f / 255f, 1.0f);
        } else if (menu.getBeerType() == 5) {
            RenderSystem.setShaderColor(170f / 255f, 14f / 255f,
                    1f / 255f, 1.0f);
        } else if (menu.getBeerType() == 6) {
            RenderSystem.setShaderColor(75f / 255f, 165f / 255f,
                    128f / 255f, 1.0f);
        } else if (menu.getBeerType() == 7) {
            RenderSystem.setShaderColor(58f / 255f, 70f / 255f,
                    123f / 255f, 1.0f);
        } else if (menu.getBeerType() == 8) {
            RenderSystem.setShaderColor(143f / 255f, 181f / 255f,
                    246f / 255f, 1.0f);
        } else if (menu.getBeerType() == 9) {
            RenderSystem.setShaderColor(188f / 255f, 137f / 255f,
                    39f / 255f, 1.0f);
        } else if (menu.getBeerType() == 10) {
            RenderSystem.setShaderColor(69f / 255f, 201f / 255f,
                    72f / 255f, 1.0f);
        } else if (menu.getBeerType() == 11) {
            RenderSystem.setShaderColor(142f / 255f, 102f / 255f,
                    141f / 255f, 1.0f);
        } else if (menu.getBeerType() == 12) {
            RenderSystem.setShaderColor(199f / 255f, 183f / 255f,
                    1.0f, 1.0f);
        }

        for (int j = 0; j <= 3; j++) {
            for (int i = 0; i <= 8; i++) {
                this.blit(pPoseStack, x + 16 * i, y + 16 * j, 0, 16, 16, sprite);
            }
        }

        //Gui
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        x = (width - imageWidth) / 2;
        y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight+2);

        if (this.menu.isCrafting()) {
            int i1 = this.menu.getProgress();
            int j1 = BUBBLELENGTHS[i1 / 2 % 7];

            this.blit(pPoseStack, x + 150, y + 17 + 28 -j1, 176, 28 - j1, 11, j1);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
