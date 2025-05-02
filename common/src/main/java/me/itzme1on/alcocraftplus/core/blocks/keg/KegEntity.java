package me.itzme1on.alcocraftplus.core.blocks.keg;

import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.helpers.ImplementedInventory;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.utils.BeerTypeMapperUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
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
        beerLevel = nbt.getInt("beerLevel");
        beerType = nbt.getInt("beerType");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, inventory);
        nbt.putInt("progress", progress);
        nbt.putInt("waterLevel", waterLevel);
        nbt.putInt("beerLevel", beerLevel);
        nbt.putInt("beerType", beerType);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return false;
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
        SimpleContainer inventoryWrapper = new SimpleContainer(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventoryWrapper.setItem(i, entity.inventory.get(i));
        }

        Optional<KegRecipes> match = pLevel.getRecipeManager()
                .getRecipeFor(RecipesRegistry.KEG_RECIPE_TYPE.get(), inventoryWrapper, pLevel);

        match.ifPresent(recipe -> {
            for (int i = 0; i < entity.inventory.size(); i++) {
                ItemStack stackInSlot = entity.inventory.get(i);
                if (!stackInSlot.isEmpty()) {
                    boolean isIngredient = recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stackInSlot));
                    if (isIngredient) {
                        stackInSlot.shrink(1);
                    }
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