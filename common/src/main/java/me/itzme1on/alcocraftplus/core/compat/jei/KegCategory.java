package me.itzme1on.alcocraftplus.core.compat.jei;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class KegCategory implements IRecipeCategory<KegRecipes> {
    public static final IRecipeType<KegRecipes> RECIPE_TYPE = IRecipeType.create(AlcoCraftPlus.MOD_ID, "beer_brewing", KegRecipes.class);

    public final static Identifier TEXTURE = IdentifierUtil.of("textures/gui/jei_gui.png");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    private final int BACKGROUND_WIDTH = 176;
    private final int BACKGROUND_HEIGHT = 48;

    private final int SLOTS_Y_POSITION = 16;

    public KegCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksRegistry.KEG.get()));
        this.arrow = helper.drawableBuilder(TEXTURE, BACKGROUND_WIDTH, 0, 22, 16)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).add(ingredient);
    }

    @Override
    public @NotNull IRecipeType<KegRecipes> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("block.alcocraftplus.keg");
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public int getWidth() {
        return BACKGROUND_WIDTH;
    }

    @Override
    public int getHeight() {
        return BACKGROUND_HEIGHT;
    }

    @Override
    public void draw(@NotNull KegRecipes recipe, @NotNull IRecipeSlotsView recipeSlotsView,
                     @NotNull GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        arrow.draw(guiGraphics, 113, SLOTS_Y_POSITION - 1);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull KegRecipes recipe,
                          @NotNull IFocusGroup focuses) {
        int count = Math.min(4, recipe.getIngredients().size());

        for (int i = 0; i < count; i++) {
            addSlot(builder, 12 + 24 * i, SLOTS_Y_POSITION, recipe.getIngredients().get(i));
        }

        builder.setShapeless(163, 4);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 148, SLOTS_Y_POSITION).add(recipe.getResultItem());
    }
}
