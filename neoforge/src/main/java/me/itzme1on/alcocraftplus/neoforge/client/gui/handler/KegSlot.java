package me.itzme1on.alcocraftplus.neoforge.client.gui.handler;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class KegSlot extends Slot {
    private final Predicate<ItemStack> filter;

    public KegSlot(Container inventory, int index, int x, int y, Predicate<ItemStack> filter) {
        super(inventory, index, x, y);
        this.filter = filter;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.filter.test(stack);
    }
}