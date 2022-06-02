package net.hadrus.alcocraft.gui;

import net.minecraft.world.inventory.DataSlot;

public class ResultDataSlot extends DataSlot {
    private int amount;

    @Override
    public int get() {
        return amount;
    }

    @Override
    public void set(int pValue) {
        amount = pValue;
    }
}
