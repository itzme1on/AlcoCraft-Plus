package me.itzme1on.alcocraftplus.fabric.core.compat.rei;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KegCategory implements IRecipeCategory<KegRecipes> {
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(AlcoCraftPlus.MOD_ID, "textures/gui/jei_gui.png");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    private final int BACKGROUND_WIDTH = 176;
    private final int BACKGROUND_HEIGHT = 48;
    private final int ARROW_WIDTH = 22;
    private final int ARROW_HEIGHT = 16;

    private final int SLOTS_Y_POSITION = 16;

    public KegCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksRegistry.KEG.get()));
        this.arrow = helper.drawableBuilder(TEXTURE, BACKGROUND_WIDTH, 0, ARROW_WIDTH, ARROW_HEIGHT)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull RecipeType<KegRecipes> getRecipeType() {
        return JEIPlugin.RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.alcocraftplus.keg");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(KegRecipes recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 113, SLOTS_Y_POSITION - 1);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KegRecipes recipe, IFocusGroup focuses) {
        for (int i = 0; i < 4; i++)
            builder.addSlot(RecipeIngredientRole.INPUT, 12 + 24 * i, SLOTS_Y_POSITION).addIngredients(recipe.getIngredients().get(i));

        builder.setShapeless(163, 4);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 148, SLOTS_Y_POSITION).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}