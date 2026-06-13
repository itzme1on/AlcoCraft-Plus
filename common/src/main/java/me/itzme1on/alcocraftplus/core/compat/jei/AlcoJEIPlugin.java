package me.itzme1on.alcocraftplus.core.compat.jei;

import me.itzme1on.alcocraftplus.core.network.ClientKegData;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
public class AlcoJEIPlugin implements IModPlugin {
    private static IJeiRuntime runtime;
    private static boolean pushed;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KegCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(KegCategory.RECIPE_TYPE, new ItemStack(BlocksRegistry.KEG.get()));
    }

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
        pushed = false;
        ClientKegData.setUpdateListener(AlcoJEIPlugin::pushKegRecipes);
        pushKegRecipes();
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
        pushed = false;
    }

    private static void pushKegRecipes() {
        IJeiRuntime jeiRuntime = runtime;
        if (jeiRuntime == null || pushed) return;

        List<KegRecipes> recipes = ClientKegData.recipes();
        if (recipes.isEmpty()) return;

        jeiRuntime.getRecipeManager().addRecipes(KegCategory.RECIPE_TYPE, recipes);
        pushed = true;
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return IdentifierUtil.of("jei_plugin");
    }
}
