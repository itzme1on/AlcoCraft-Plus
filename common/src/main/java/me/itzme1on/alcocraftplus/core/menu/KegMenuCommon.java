package me.itzme1on.alcocraftplus.core.menu;

import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry; // Added import
import me.itzme1on.alcocraftplus.core.registries.MenuRegistry;
import net.minecraft.core.BlockPos; // Added import
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class KegMenuCommon extends AbstractContainerMenu {

    private final Container kegInventory;
    private final Level level; // Keep level for other potential uses if needed
    private final ContainerData data;
    private final ContainerLevelAccess access; // Added field for level access

    // Constructor for server-side creation (called from KegEntity.createMenu)
    public KegMenuCommon(int windowId, Inventory playerInventory, KegEntity kegEntity, ContainerData data) {
        // Line 24: Pass KegEntity directly to the common constructor
        this(MenuRegistry.KEG_MENU.get(), windowId, playerInventory, kegEntity, data);
    }

    // Constructor for client-side creation (called via network)
    // Also used by the server-side constructor to delegate common logic
    public KegMenuCommon(MenuType<?> menuType, int windowId, Inventory playerInventory, BlockEntity blockEntity, ContainerData data) {
        super(menuType, windowId);
        // Line 32: Ensure we have the correct number of slots (4 for Keg)
        // Line 33: Check container size based on BlockEntity type
        checkContainerSize(blockEntity instanceof Container ? (Container) blockEntity : new SimpleContainer(4), 4);
        checkContainerDataCount(data, 5); // Ensure we have 5 data values

        // Line 37: Store references
        // Line 38: Get inventory from KegEntity or create dummy for client
        this.kegInventory = blockEntity instanceof KegEntity ? new SimpleContainer(((KegEntity) blockEntity).getInventory().toArray(new ItemStack[0])) : new SimpleContainer(4);
        // Line 40: Store level from player inventory
        this.level = playerInventory.player.level();
        this.data = data;
        // Line 43: Create and store ContainerLevelAccess using the BlockEntity's level and position
        this.access = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Line 46: Define Keg Slots (4 input slots)
        int kegSlotX = 12;
        int kegSlotY = 16;
        int kegSlotSpacing = 24;
        for (int i = 0; i < 4; ++i) {
            this.addSlot(new Slot(this.kegInventory, i, kegSlotX + i * kegSlotSpacing, kegSlotY));
        }

        // Line 55: Define Player Inventory Slots
        int playerInvX = 8;
        int playerInvY = 86;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, playerInvX + col * 18, playerInvY + row * 18));
            }
        }

        // Line 64: Define Player Hotbar Slots
        int hotbarY = 144;
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, playerInvX + i * 18, hotbarY));
        }

        // Line 70: Add data slots to sync ContainerData
        this.addDataSlots(data);
    }

    // Line 74: Check if player is still near the Keg using ContainerLevelAccess
    @Override
    public boolean stillValid(Player player) {
        // Line 77: Use the stored ContainerLevelAccess and the static stillValid helper
        return stillValid(this.access, player, BlocksRegistry.KEG.get());
    }

    // Line 81: Handle shift-clicking (quickMoveStack) - NO CHANGES HERE
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            final int kegSlots = 4;
            final int playerInvStart = kegSlots;
            final int playerInvEnd = playerInvStart + 27;
            final int hotbarStart = playerInvEnd;
            final int hotbarEnd = hotbarStart + 9;

            if (index < kegSlots) {
                if (!this.moveItemStackTo(slotStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= playerInvStart) {
                if (!this.moveItemStackTo(slotStack, 0, kegSlots, false)) {
                    if (index < playerInvEnd) {
                        if (!this.moveItemStackTo(slotStack, hotbarStart, hotbarEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index < hotbarEnd) {
                        if (!this.moveItemStackTo(slotStack, playerInvStart, playerInvEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return itemstack;
    }

    // --- Helper methods to access synced data --- NO CHANGES HERE

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 22;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getWaterLevel() {
        return this.data.get(2);
    }

    public int getBeerLevel() {
        return this.data.get(3);
    }

    public int getBeerType() {
        return this.data.get(4);
    }

    // Removed the commented-out getBlockPos method as it's no longer needed here
}