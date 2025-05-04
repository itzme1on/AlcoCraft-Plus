package me.itzme1on.alcocraftplus.fabric.client.gui.handler;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class KegSlot extends Slot {
    private final Predicate<ItemStack> filter;

    public KegSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> filter) {
        super(inventory, index, x, y);
        this.filter = filter;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return this.filter.test(stack);
    }
}