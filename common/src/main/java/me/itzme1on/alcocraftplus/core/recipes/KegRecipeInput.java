package me.itzme1on.alcocraftplus.core.recipes;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class KegRecipeInput implements RecipeInput {
    private final NonNullList<ItemStack> items;

    public KegRecipeInput(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }
}

