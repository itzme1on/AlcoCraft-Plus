package me.itzme1on.alcocraftplus.core.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class KegRecipes implements Recipe<SimpleContainer> {
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public KegRecipes(ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (container == null) return false;

        if (container.getContainerSize() != recipeItems.size()) return false;

        NonNullList<Ingredient> remainingIngredients = NonNullList.create();
        remainingIngredients.addAll(recipeItems);

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack itemInContainer = container.getItem(i);

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
        return recipeItems;
    }

    @Override
    public @NotNull ItemStack assemble(SimpleContainer container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 4;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.KEG_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipesRegistry.KEG_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<KegRecipes> {
        @Override
        public @NotNull Codec<KegRecipes> codec() {
            return RecordCodecBuilder.create(instance -> instance.group(
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.recipeItems)
            ).apply(instance, (output, ingredients) -> new KegRecipes(
                    output,
                    NonNullList.of(Ingredient.EMPTY, ingredients.toArray(new Ingredient[0]))
            )));
        }

        @Override
        public @NotNull KegRecipes fromNetwork(FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            ItemStack output = buf.readItem();

            return new KegRecipes(output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, KegRecipes recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) ing.toNetwork(buf);
            buf.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }

    public static NonNullList<KegRecipes> getAll(Level world) {
        return world.getRecipeManager().getRecipes().stream()
                .filter(recipeHolder -> recipeHolder.value().getType() == RecipesRegistry.KEG_RECIPE_TYPE.get())
                .map(recipe -> (KegRecipes) recipe.value())
                .collect(Collectors.toCollection(NonNullList::create));
    }
}
