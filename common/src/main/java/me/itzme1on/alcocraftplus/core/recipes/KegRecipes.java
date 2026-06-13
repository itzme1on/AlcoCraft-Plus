package me.itzme1on.alcocraftplus.core.recipes;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class KegRecipes implements Recipe<RecipeInput>, HasIngredients {
    private final ItemStackTemplate output;
    private final NonNullList<Ingredient> recipeItems;

    public KegRecipes(ItemStackTemplate output, NonNullList<Ingredient> recipeItems) {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(RecipeInput inventory, Level level) {
        if (inventory == null) return false;

        if (inventory.size() != recipeItems.size()) return false;

        NonNullList<Ingredient> remainingIngredients = NonNullList.create();
        remainingIngredients.addAll(recipeItems);

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemInContainer = inventory.getItem(i);

            if (!itemInContainer.isEmpty()) {
                boolean foundMatch = false;

                for (int j = 0; j < remainingIngredients.size(); j++)
                    if (remainingIngredients.get(j).test(itemInContainer)) {
                        remainingIngredients.remove(j);

                        foundMatch = true;

                        break;
                    }

                if (!foundMatch) return false;
            }
        }

        return remainingIngredients.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput input) {
        return output.create();
    }

    public @NotNull ItemStack getResultItem() {
        return output.create();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return RecipesRegistry.KEG_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<RecipeInput>> getType() {
        return RecipesRegistry.KEG_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public @NotNull String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public List<Ingredient> alcocraftplus$ingredients() {
        return Collections.unmodifiableList(this.recipeItems);
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public static final MapCodec<KegRecipes> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
                        if (list.isEmpty()) {
                            return DataResult.error(() -> "No ingredients for keg recipe");
                        }

                        NonNullList<Ingredient> nonNullList = NonNullList.create();

                        nonNullList.addAll(list);

                        return DataResult.success(nonNullList);
                    }, DataResult::success).forGetter(KegRecipes::getIngredients),

                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
            ).apply(instance, (ingredients, result) -> new KegRecipes(result, ingredients))
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, NonNullList<Ingredient>> INGREDIENTS_LIST_CODEC =
            ByteBufCodecs.collection(NonNullList::createWithCapacity, Ingredient.CONTENTS_STREAM_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, KegRecipes> STREAM_CODEC = StreamCodec.composite(
            INGREDIENTS_LIST_CODEC, KegRecipes::getIngredients,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.output,
            (ingredients, result) -> new KegRecipes(result, ingredients)
    );
}
