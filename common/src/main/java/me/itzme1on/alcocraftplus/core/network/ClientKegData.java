package me.itzme1on.alcocraftplus.core.network;

import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ClientKegData {
    private static volatile List<KegRecipes> recipes = List.of();
    private static volatile Set<Identifier> acceptedItems = Set.of();
    private static volatile Runnable updateListener;

    private ClientKegData() {
    }

    public static void set(List<KegRecipes> newRecipes) {
        recipes = newRecipes;

        Set<Identifier> items = new HashSet<>();
        for (KegRecipes recipe : newRecipes) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.items().forEach(item -> items.add(BuiltInRegistries.ITEM.getKey(item.value())));
            }
        }
        acceptedItems = items;

        Runnable listener = updateListener;
        if (listener != null) listener.run();
    }

    public static List<KegRecipes> recipes() {
        return recipes;
    }

    public static void setUpdateListener(Runnable listener) {
        updateListener = listener;
    }

    public static boolean isEmpty() {
        return acceptedItems.isEmpty();
    }

    public static boolean accepts(Item item) {
        return acceptedItems.contains(BuiltInRegistries.ITEM.getKey(item));
    }
}
