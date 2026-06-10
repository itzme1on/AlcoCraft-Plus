package me.itzme1on.alcocraftplus.neoforge.core.compat.jei;

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
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class NeoForgeJEICompat implements IModPlugin {
    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, net.minecraft.world.item.crafting.Ingredient ingredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).add(ingredient);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new NeoForgeKegCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) return;

        RecipeManager rm = server.getRecipeManager();

        List<KegRecipes> kegRecipes = rm.getRecipes().stream()
                .map(RecipeHolder::value)
                .filter(r -> r.getType() == RecipesRegistry.KEG_RECIPE_TYPE.get())
                .map(r -> (KegRecipes) r)
                .toList();

        registration.addRecipes(NeoForgeKegCategory.RECIPE_TYPE, kegRecipes);
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return IdentifierUtil.of("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(NeoForgeKegCategory.RECIPE_TYPE, new ItemStack(BlocksRegistry.KEG.get()));
    }
}