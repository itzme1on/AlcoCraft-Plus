package me.itzme1on.alcocraftplus.core.compat.jei;

import me.itzme1on.alcocraftplus.core.compat.jei.keg.KegCategory;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JeiPlugin
public class AlcoJEIPlugin implements IModPlugin {
    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KegCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<RecipeHolder<KegRecipes>> kegHolderRecipes = rm.getAllRecipesFor(RecipesRegistry.KEG_RECIPE_TYPE.get());
        List<KegRecipes> kegRecipes = new ArrayList<>();

        kegHolderRecipes.iterator().forEachRemaining(recipeHolder -> kegRecipes.add(recipeHolder.value()));

        registration.addRecipes(KegCategory.RECIPE_TYPE, kegRecipes);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return IdentifierUtil.of("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlocksRegistry.KEG.get()), KegCategory.RECIPE_TYPE);
    }
}
