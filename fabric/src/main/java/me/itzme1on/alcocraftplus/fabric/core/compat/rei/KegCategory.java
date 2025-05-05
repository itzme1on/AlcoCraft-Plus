package me.itzme1on.alcocraftplus.fabric.core.compat.rei;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
// Import the common recipe definition (assuming you move/created it here)
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;

// REI Imports
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;

// Minecraft Imports
import net.minecraft.network.chat.Component; // Use Mojang name
import net.minecraft.resources.ResourceLocation; // Use Mojang name
import net.minecraft.text.Text; // Fabric's Text for translatable

import java.util.ArrayList;
import java.util.List;

// Note: This category now works with KegDisplay, not KegRecipes directly.
// You will need to create a KegDisplay class.
public class KegCategory implements DisplayCategory<KegDisplay> {

    // Texture remains the same, but use ResourceLocation
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/jei_gui.png"); // Keep using the same texture

    // Dimensions from the JEI version
    private static final int BACKGROUND_WIDTH = 176;
    private static final int BACKGROUND_HEIGHT = 48;
    private static final int ARROW_TEXTURE_X = 176; // U coordinate of arrow in texture
    private static final int ARROW_TEXTURE_Y = 0;   // V coordinate of arrow in texture
    private static final int ARROW_WIDTH = 22;
    private static final int ARROW_HEIGHT = 16;
    private static final int ARROW_DRAW_X = 113; // Relative X position to draw arrow
    private static final int ARROW_DRAW_Y = 15;  // Relative Y position to draw arrow (16 - 1 from JEI)

    private static final int SLOTS_START_X = 12;
    private static final int SLOTS_SPACING = 24;
    private static final int SLOTS_Y_POSITION = 16;
    private static final int OUTPUT_SLOT_X = 148;


    // No IGuiHelper needed in constructor for REI basic setup

    @Override
    public CategoryIdentifier<? extends KegDisplay> getCategoryIdentifier() {
        // Get the identifier defined in your REI plugin
        return AlcoCraftREIPlugin.KEG_RECIPE_TYPE;
    }

    @Override
    public Text getTitle() {
        // Use Fabric's Text helper for translatable components
        return Text.translatable("block.alcocraftplus.keg");
    }

    @Override
    public Renderer getIcon() {
        // Use EntryStacks for the icon
        return EntryStacks.of(BlocksRegistry.KEG.get());
    }

    @Override
    public List<Widget> setupDisplay(KegDisplay display, Rectangle bounds) {
        // 'bounds' represents the area REI gives us for the recipe layout.
        // We usually position everything relative to bounds.x and bounds.y.
        Point startPoint = new Point(bounds.x, bounds.y); // Top-left corner of the bounds

        List<Widget> widgets = new ArrayList<>();

        // 1. Add the background widget
        widgets.add(Widgets.createTexturedWidget(
                TEXTURE,                      // Texture Identifier
                startPoint.x,                 // X position (top-left)
                startPoint.y,                 // Y position (top-left)
                0,                            // U coordinate in texture for background
                0,                            // V coordinate in texture for background
                BACKGROUND_WIDTH,             // Width of the background area to draw
                BACKGROUND_HEIGHT             // Height of the background area to draw
        ));

        // 2. Add the input slots
        List<EntryIngredient> inputEntries = display.getInputEntries();
        for (int i = 0; i < 4; i++) {
            // Calculate position relative to startPoint
            int slotX = startPoint.x + SLOTS_START_X + (i * SLOTS_SPACING);
            int slotY = startPoint.y + SLOTS_Y_POSITION;
            widgets.add(Widgets.createSlot(new Point(slotX, slotY))
                    .entries(inputEntries.get(i)) // Get ingredients for this slot from the KegDisplay
                    .markInput());
        }

        // 3. Add the output slot
        int outputX = startPoint.x + OUTPUT_SLOT_X;
        int outputY = startPoint.y + SLOTS_Y_POSITION;
        widgets.add(Widgets.createSlot(new Point(outputX, outputY))
                .entries(display.getOutputEntries().get(0)) // Get the output from KegDisplay
                .markOutput());

        // 4. Add the animated arrow (using the specific texture part)
        int arrowX = startPoint.x + ARROW_DRAW_X;
        int arrowY = startPoint.y + ARROW_DRAW_Y;
        widgets.add(Widgets.createAnimatedWidget(
                TEXTURE,                      // Texture Identifier
                arrowX,                       // X position to draw arrow
                arrowY,                       // Y position to draw arrow
                ARROW_TEXTURE_X,              // U coordinate in texture for arrow
                ARROW_TEXTURE_Y,              // V coordinate in texture for arrow
                ARROW_WIDTH,                  // Width of the arrow frame
                ARROW_HEIGHT,                 // Height of the arrow frame
                200,                          // Animation duration (ticks) - same as JEI
                true,                         // Horizontal animation? (yes, left-to-right usually)
                false                         // Reverse animation? (no)
        ));
        // Alternative simpler arrow (uses default REI arrow texture):
        // widgets.add(Widgets.createArrow(new Point(arrowX, arrowY)).animationDurationTicks(200));


        return widgets; // Return the list of widgets for REI to draw
    }

    @Override
    public int getDisplayHeight() {
        // Return the height of your background/recipe area
        return BACKGROUND_HEIGHT;
    }

    @Override
    public int getDisplayWidth(KegDisplay display) {
        // Return the width of your background/recipe area
        return BACKGROUND_WIDTH;
    }
}