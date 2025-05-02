// Suggested package: me.itzme1on.alcocraftplus.core.compat.rei
package me.itzme1on.alcocraftplus.core.compat.rei;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.List;

/**
 * The main REI plugin class for AlcoCraftPlus.
 * This replaces the functionality of the old JEIPlugin.
 */
public class AlcoCraftREIPlugin implements REIClientPlugin {

    /**
     * Unique identifier for the Keg/Beer Brewing recipe category.
     * Replaces JEI's RecipeType. It's typed with the Display class (KegDisplay).
     */
    public static final CategoryIdentifier<KegDisplay> KEG_RECIPE_TYPE =
            CategoryIdentifier.of(AlcoCraftPlus.MOD_ID, "beer_brewing"); // Use the same ID as before

    /**
     * Registers the recipe category handlers and their associated workstations (catalysts).
     * Replaces JEI's registerCategories and registerRecipeCatalysts.
     * @param registry The category registry provided by REI.
     */
    @Override
    public void registerCategories(CategoryRegistry registry) {
        // 1. Register the visual category handler (KegCategory)
        //    We don't need the IGuiHelper here; drawing is handled within KegCategory.
        registry.add(new KegCategory()); // Assumes KegCategory class exists

        // 2. Register the Keg block as a "workstation" (catalyst) for this category.
        //    This tells REI that clicking the Keg block should show KEG_RECIPE_TYPE recipes.
        registry.addWorkstations(KEG_RECIPE_TYPE, EntryStacks.of(BlocksRegistry.KEG.get()));

        // Add more workstations here if other blocks can perform these recipes
        // registry.addWorkstations(KEG_RECIPE_TYPE, EntryStacks.of(OTHER_BLOCK.get()));
    }

    /**
     * Registers the actual recipe instances (wrapped in Display objects).
     * Replaces JEI's registerRecipes.
     * @param registry The display registry provided by REI.
     */
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        // Get the recipes using the same logic as in JEIPlugin.
        // Ensure this is client-safe.
        ClientLevel level = Minecraft.getInstance().level; // Use level instead of world for clarity
        if (level != null) { // Good practice to check for null level
            List<KegRecipes> allRecipes = KegRecipes.getAll(level);

            // For each KegRecipe, create a KegDisplay and register it.
            for (KegRecipes recipe : allRecipes) {
                registry.add(new KegDisplay(recipe)); // Assumes KegDisplay class exists and takes KegRecipes
            }
        }

        // Alternative (if KegRecipes implemented Recipe<?> and had a RecipeSerializer):
        // registry.registerRecipeFiller(KegRecipes.class, KegRecipes.TYPE, KegDisplay::new);
        // But the manual loop above is perfectly fine for custom recipe types.
    }

    // Optional: Override getPluginProviderName() for better logging/debugging
    // @Override
    // public String getPluginProviderName() {
    //     return "AlcoCraftPlus REI Plugin";
    // }

    // Optional: Override other methods like registerExclusionZones, registerScreens, etc. if needed.
}