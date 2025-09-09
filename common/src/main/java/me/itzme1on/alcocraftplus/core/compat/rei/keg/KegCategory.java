package me.itzme1on.alcocraftplus.core.compat.rei.keg;

import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplayMerger;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KegCategory implements DisplayCategory<KegDisplay> {
    private static final ResourceLocation BG_TEXTURE = IdentifierUtil.of("textures/gui/jei_gui.png");

    private static final int BG_WIDTH = 176;
    private static final int BG_HEIGHT = 48;
    private static final int SLOTS_Y_POSITION = 16;

    @Override
    public CategoryIdentifier<? extends KegDisplay> getCategoryIdentifier() {
        return KegDisplay.CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alcocraftplus.keg");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(BlocksRegistry.KEG.get());
    }

    @Override
    public List<Widget> setupDisplay(KegDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG_TEXTURE, bounds.x, bounds.y, 0, 0, BG_WIDTH, BG_HEIGHT));

        int startX = bounds.x + 12;
        int startY = bounds.y + SLOTS_Y_POSITION;

        for (int i = 0; i < Math.min(4, display.getInputEntries().size()); i++) {
            int x = startX + 24 * i;

            widgets.add(Widgets.createSlot(new Point(x, startY))
                    .entries(display.getInputEntries().get(i))
                    .disableBackground()
                    .markInput()
            );
        }

        widgets.add(new CustomArrowWidget(
                bounds.x + 113,
                bounds.y + SLOTS_Y_POSITION - 1,
                BG_TEXTURE,
                BG_WIDTH,
                0,
                22,
                16,
                10 * 1000
        ));

        if (!display.getOutputEntries().isEmpty())
            widgets.add(Widgets.createSlot(new Point(bounds.x + 148, bounds.y + SLOTS_Y_POSITION))
                    .entries(display.getOutputEntries().getFirst())
                    .disableBackground()
                    .markOutput()
            );

        return widgets;
    }

    @Override
    public int getDisplayWidth(KegDisplay display) {
        return BG_WIDTH;
    }

    @Override
    public int getDisplayHeight() {
        return BG_HEIGHT;
    }

    @Override
    public DisplayMerger<KegDisplay> getDisplayMerger() {
        return DisplayCategory.getContentMerger();
    }

    private static class CustomArrowWidget extends WidgetWithBounds {
        private final Rectangle bounds;
        private final ResourceLocation texture;
        private final int textureU, textureV, arrowWidth, arrowHeight;
        private final long durationMs;

        CustomArrowWidget(int x, int y, ResourceLocation texture,
                          int textureU, int textureV,
                          int arrowWidth, int arrowHeight,
                          long durationMs) {
            this.bounds = new Rectangle(x, y, arrowWidth, arrowHeight);
            this.texture = texture;
            this.textureU = textureU;
            this.textureV = textureV;
            this.arrowWidth = arrowWidth + 1;
            this.arrowHeight = arrowHeight;
            this.durationMs = durationMs;
        }

        @Override
        public Rectangle getBounds() {
            return bounds;
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
            long now = System.currentTimeMillis();
            long elapsed = now % durationMs;

            int progressWidth = (int) Math.floor(((elapsed + 1) * arrowWidth) / (double) durationMs);

            if (progressWidth > arrowWidth) progressWidth = arrowWidth;

            if (progressWidth <= 0) return;

            graphics.blit(RenderType::guiTextured, texture,
                    bounds.x, bounds.y, (float) textureU, (float) textureV,
                    progressWidth, arrowHeight, 256, 256);
        }

        @Override
        public java.util.@NotNull List<? extends GuiEventListener> children() {
            return java.util.Collections.emptyList();
        }
    }
}
