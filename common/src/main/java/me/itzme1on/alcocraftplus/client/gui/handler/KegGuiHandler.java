package me.itzme1on.alcocraftplus.client.gui.handler;

import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.registries.ScreenHandlerRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class KegGuiHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final Level level;
    private final ContainerData propertyDelegate;

    public KegGuiHandler(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(4), new SimpleContainerData(5));
    }

    public KegGuiHandler(int containerId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(ScreenHandlerRegistry.KEG_MENU.get(), containerId);
        checkContainerSize(inventory, 4);

        this.inventory = inventory;
        this.level = playerInventory.player.level();
        this.propertyDelegate = propertyDelegate;

        inventory.startOpen(playerInventory.player);

        this.addSlot(new KegSlot(inventory, 0, 22, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 1, 50, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 2, 78, 27, this::isIngredient));
        this.addSlot(new KegSlot(inventory, 3, 106, 27, this::isIngredient));

        addPlayerInventory(playerInventory);
        addPlayerHotBar(playerInventory);

        addDataSlots(propertyDelegate);
    }

    public boolean isCrafting(int index) {
        return propertyDelegate.get(index * 2) > 0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (index < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private boolean isIngredient(ItemStack stack) {
        return this.level.getRecipeManager()
                .getAllRecipesFor(RecipesRegistry.KEG_RECIPE_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    public int getProgress() {
        return propertyDelegate.get(0);
    }

    public int getMaxProgress() {
        return propertyDelegate.get(1);
    }

    public int getWaterLevel() {
        return propertyDelegate.get(2);
    }

    public int getBeerLevel() {
        return propertyDelegate.get(3);
    }

    public int getBeerType() {
        return propertyDelegate.get(4);
    }
}
