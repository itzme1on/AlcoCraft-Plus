package me.itzme1on.alcocraftplus.core.recipes;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.utils.StreamCodecUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class KegRecipes implements Recipe<RecipeInput> {
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public KegRecipes(ItemStack output, NonNullList<Ingredient> recipeItems) {
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
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 4;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.KEG_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.KEG_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<KegRecipes> {
        public static final MapCodec<KegRecipes> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap(list -> {
                            Ingredient[] ingredients = list.toArray(Ingredient[]::new);
                            if (ingredients.length == 0)
                                return DataResult.error(() -> "No ingredients for shapeless recipe");

                            return DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
                        }, DataResult::success).forGetter(KegRecipes::getIngredients),

                        ItemStack.CODEC.optionalFieldOf("result").forGetter(recipe -> Optional.of(recipe.output)),
                        ItemStack.CODEC.optionalFieldOf("output").forGetter(recipe -> Optional.empty())
                ).apply(instance, (ingredients, resultOpt, outputOpt) -> new KegRecipes(resultOpt.orElseGet(() -> outputOpt.orElse(ItemStack.EMPTY)), ingredients))
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, KegRecipes> STREAM_CODEC = StreamCodec.composite(
                StreamCodecUtil.nonNullList(Ingredient.CONTENTS_STREAM_CODEC, Ingredient.EMPTY), KegRecipes::getIngredients,
                ItemStack.STREAM_CODEC, recipe -> recipe.output,
                (ingredients, result) -> new KegRecipes(result, ingredients)
        );

        @Override
        public @NotNull MapCodec<KegRecipes> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, KegRecipes> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
