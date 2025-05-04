package me.itzme1on.alcocraftplus.fabric.client.gui.handler;

import me.itzme1on.alcocraftplus.fabric.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.fabric.core.registries.ScreenHandlerRegistry;
import me.itzme1on.alcocraftplus.fabric.client.gui.handler.KegSlot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

public class KegGuiHandler extends ScreenHandler {
    private final Inventory inventory;
    private final World world;
    private final PropertyDelegate propertyDelegate;

    public KegGuiHandler(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(5));
    }

    public KegGuiHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ScreenHandlerRegistry.KEG_MENU.get(), syncId);
        checkSize(inventory, 4);

        this.inventory = inventory;
        this.world = playerInventory.player.getWorld();
        this.propertyDelegate = propertyDelegate;

        inventory.onOpen(playerInventory.player);

        this.addSlot(new KegSlot(inventory, 0, 22, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 1, 50, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 2, 78, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 3, 106, 27, this::isIngredient));

        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);

        addProperties(propertyDelegate);
    }

    public boolean isCrafting(int index) {

        return propertyDelegate.get(index * 2) > 0;
    }

    @Override
    public @NotNull ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) { // Use hasStack()
            ItemStack originalStack = slot.getStack(); // Use getStack()
            newStack = originalStack.copy();
            // Check if slot is in the Keg inventory (index < inventory size)
            if (invSlot < this.inventory.size()) { // Use inventory.size()
                // Try to move from Keg to player inventory
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Try to move from player inventory to Keg inventory
                // You might want more specific logic here based on KegSlot's canInsert
                if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.world.getRecipeManager()
                .listAllOfType(RecipesRegistry.KEG_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    public int getProgress() { return propertyDelegate.get(0); }
    public int getMaxProgress() { return propertyDelegate.get(1); }
    public int getWaterLevel() { return propertyDelegate.get(2); }
    public int getBeerLevel() { return propertyDelegate.get(3); }
    public int getBeerType() { return propertyDelegate.get(4); }
}
