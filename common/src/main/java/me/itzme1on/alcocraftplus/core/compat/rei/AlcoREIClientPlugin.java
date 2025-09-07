package me.itzme1on.alcocraftplus.core.compat.rei;

import me.itzme1on.alcocraftplus.core.compat.rei.keg.KegCategory;
import me.itzme1on.alcocraftplus.core.compat.rei.keg.KegDisplay;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.ArrayList;
import java.util.List;

public class AlcoREIClientPlugin implements REIClientPlugin {
    public static List<Ingredient> ingredients(Recipe<RecipeInput> recipe, ItemStack stack) {
        List<Ingredient> ingredients = new ArrayList<>(recipe.getIngredients());

        ingredients.addFirst(Ingredient.of(stack.getItem()));

        return ingredients;
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new KegCategory());
        registry.addWorkstations(KegDisplay.CATEGORY, EntryStacks.of(BlocksRegistry.KEG.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(
                KegRecipes.class,
                RecipesRegistry.KEG_RECIPE_TYPE.get(),
                holder -> KegDisplay.of(holder.value())
        );
    }
}
