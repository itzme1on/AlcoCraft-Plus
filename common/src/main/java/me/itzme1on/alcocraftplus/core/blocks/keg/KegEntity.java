package me.itzme1on.alcocraftplus.core.blocks.keg;

import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.helpers.ImplementedInventory;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.utils.BeerTypeMapperUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KegEntity extends BlockEntity implements MenuProvider, ImplementedInventory, BlockEntityTicker<KegEntity> {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);

    private int progress = 0;

    private final int maxProgress = 18000;
    public final int maxWaterLevel = 40;

    public int waterLevel = 0;
    public int beerLevel = 0;
    public int beerType = 0;

    public int waterRemainderMb = 0;
    public int beerRemainderMb = 0;

    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> waterLevel;
                case 3 -> beerLevel;
                case 4 -> beerType;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 2 -> waterLevel = value;
                case 3 -> beerLevel = value;
                case 4 -> beerType = value;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    };

    public KegEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesRegistry.KEG_ENTITY.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.alcocraftplus.keg");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new KegGuiHandler(id, inventory, this, propertyDelegate);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, inventory);
        progress = nbt.getInt("progress");
        waterLevel = nbt.getInt("waterLevel");
        waterRemainderMb = nbt.getInt("waterRemainderMb");
        beerLevel = nbt.getInt("beerLevel");
        beerRemainderMb = nbt.getInt("beerRemainderMb");
        beerType = nbt.getInt("beerType");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("progress", progress);
        nbt.putInt("waterLevel", waterLevel);
        nbt.putInt("waterRemainderMb", waterRemainderMb);
        nbt.putInt("beerLevel", beerLevel);
        nbt.putInt("beerRemainderMb", beerRemainderMb);
        nbt.putInt("beerType", beerType);
    }

    public static final int MB_PER_WATER_LEVEL = 100;

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index >= 0 && index < inventory.size() && isValidIngredient(stack);
    }

    public boolean isValidIngredient(ItemStack stack) {
        if (stack.isEmpty() || level == null) return false;

        return level.getRecipeManager()
                .getAllRecipesFor(RecipesRegistry.KEG_RECIPE_TYPE.get())
                .stream()
                .anyMatch(recipe -> recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack)));
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{0, 1, 2, 3};
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, @Nullable Direction side) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction side) {
        return false;
    }

    public int getWaterMb() {
        return waterLevel * MB_PER_WATER_LEVEL + waterRemainderMb;
    }

    public int getWaterCapacityMb() {
        return maxWaterLevel * MB_PER_WATER_LEVEL;
    }

    public int fillWaterMb(int mb, boolean execute) {
        if (beerLevel > 0) return 0;

        int accept = Math.min(mb, getWaterCapacityMb() - getWaterMb());

        if (accept <= 0) return 0;

        if (execute) {
            waterRemainderMb += accept;

            int gained = waterRemainderMb / MB_PER_WATER_LEVEL;
            if (gained > 0) {
                waterLevel += gained;
                waterRemainderMb -= gained * MB_PER_WATER_LEVEL;
            }

            setChanged();
        }

        return accept;
    }

    public int getBeerMb() {
        return beerLevel * MB_PER_WATER_LEVEL + beerRemainderMb;
    }

    public int getBeerCapacityMb() {
        return maxWaterLevel * MB_PER_WATER_LEVEL;
    }

    public int drainBeerMb(int mb, boolean execute) {
        int drain = Math.min(mb, getBeerMb());

        if (drain <= 0) return 0;

        if (execute) {
            int left = getBeerMb() - drain;
            beerLevel = left / MB_PER_WATER_LEVEL;
            beerRemainderMb = left % MB_PER_WATER_LEVEL;

            if (beerLevel == 0 && beerRemainderMb == 0) beerType = 0;

            setChanged();
        }

        return drain;
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, KegEntity entity) {
        if (!level.isClientSide()) {
            RandomSource random = level.random;

            if (canBrew(entity)) {
                entity.progress++;

                if (random.nextFloat() < 0.005f) {
                    level.playSound(null, pos, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.1f, 0.5f);
                }

                if (entity.progress >= entity.maxProgress) {
                    brew(level, pos, state, entity);
                }
            } else {
                entity.resetProgress();
            }

            setChanged(level, pos, state);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.level != null && this.level.getBlockEntity(this.worldPosition) == this &&
                player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    private void brew(Level level, BlockPos pos, BlockState state, KegEntity entity) {
        Level pLevel = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.inventory.size());

        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setItem(i, entity.inventory.get(i));
        }

        Optional<KegRecipes> match = pLevel.getRecipeManager().getRecipeFor(RecipesRegistry.KEG_RECIPE_TYPE.get(), inventory, pLevel);

        match.ifPresent(recipe -> {
            for (int i = 0; i < entity.inventory.size(); i++) {
                if (!entity.inventory.get(i).isEmpty()) {
                    entity.inventory.get(i).shrink(1);
                }
            }

            entity.beerType = BeerTypeMapperUtil.getBeerType(recipe.getResultItem(level.registryAccess()).getItem());

            entity.beerLevel = entity.waterLevel;
            entity.waterLevel = 0;
            entity.resetProgress();

            setChanged(level, pos, state);
        });
    }

    private static boolean canBrew(KegEntity entity) {
        SimpleContainer container = new SimpleContainer(entity.inventory.toArray(new ItemStack[0]));

        Optional<KegRecipes> recipes = entity.level.getRecipeManager()
                .getRecipeFor(RecipesRegistry.KEG_RECIPE_TYPE.get(), container, entity.level);

        return recipes.isPresent() && !hasBeer(entity) && hasWater(entity);
    }

    private static boolean hasWater(KegEntity entity) {
        return entity.waterLevel > 0;
    }

    private static boolean hasBeer(KegEntity entity) {
        return entity.beerLevel > 0;
    }

    private void resetProgress() {
        this.progress = 0;
    }
}