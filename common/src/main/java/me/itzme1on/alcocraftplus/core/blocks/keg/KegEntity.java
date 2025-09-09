package me.itzme1on.alcocraftplus.core.blocks.keg;

import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.helpers.ImplementedInventory;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipeInput;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;
import me.itzme1on.alcocraftplus.core.registries.RecipesRegistry;
import me.itzme1on.alcocraftplus.core.utils.BeerTypeMapperUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KegEntity extends BlockEntity implements MenuProvider, ImplementedInventory, BlockEntityTicker<KegEntity> {
    public static final int MAX_BUCKETS = 4;
    public static final int MAX_MUGS = 8;
    public static final int MAX_WATER_LEVEL = MAX_MUGS * MAX_BUCKETS;
    public static final int WATER_PER_BUCKET = MAX_WATER_LEVEL / MAX_BUCKETS;
    public static final int BEER_PER_MUG = MAX_WATER_LEVEL / MAX_MUGS;
    private static final int MAX_PROGRESS = 18000;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    public int waterLevel = 0;
    public int beerLevel = 0;
    public int beerType = 0;
    private int progress = 0;

    protected final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> MAX_PROGRESS;
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

    private static Optional<net.minecraft.world.item.crafting.RecipeHolder<KegRecipes>> findMatchingRecipe(KegEntity entity) {
        if (entity.level == null || entity.level.getServer() == null) return Optional.empty();
        KegRecipeInput input = new KegRecipeInput(entity.inventory);
        return entity.level.getServer().getRecipeManager()
                .getRecipeFor(RecipesRegistry.KEG_RECIPE_TYPE.get(), input, entity.level);
    }

    private static Optional<net.minecraft.world.item.crafting.RecipeHolder<KegRecipes>> canBrewRecipe(KegEntity entity) {
        Optional<net.minecraft.world.item.crafting.RecipeHolder<KegRecipes>> match = findMatchingRecipe(entity);
        if (match.isPresent() && !hasBeer(entity) && hasWater(entity)) return match;
        return Optional.empty();
    }

    private static boolean hasWater(KegEntity entity) {
        return entity.waterLevel > 0;
    }

    private static boolean hasBeer(KegEntity entity) {
        return entity.beerLevel > 0;
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
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        ContainerHelper.loadAllItems(nbt, inventory, provider);
        progress = nbt.getInt("progress");
        waterLevel = nbt.getInt("waterLevel");
        beerLevel = nbt.getInt("beerLevel");
        beerType = nbt.getInt("beerType");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        ContainerHelper.saveAllItems(nbt, inventory, provider);
        nbt.putInt("progress", progress);
        nbt.putInt("waterLevel", waterLevel);
        nbt.putInt("beerLevel", beerLevel);
        nbt.putInt("beerType", beerType);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return index >= 0 && index < this.inventory.size();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, KegEntity entity) {
        if (!level.isClientSide()) {
            RandomSource random = level.random;

            Optional<RecipeHolder<KegRecipes>> match = canBrewRecipe(entity);
            
            if (match.isPresent()) {
                entity.progress++;

                if (random.nextFloat() < 0.005f)
                    level.playSound(null, pos, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.1f, 0.5f);

                if (entity.progress >= MAX_PROGRESS)
                    brew(level, pos, state, entity, match.get());
            } else {
                entity.resetProgress();
            }
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

    private void brew(Level level, BlockPos pos, BlockState state, KegEntity entity,
                      net.minecraft.world.item.crafting.RecipeHolder<KegRecipes> holder) {
        for (int i = 0; i < entity.inventory.size(); i++) {
            if (!entity.inventory.get(i).isEmpty()) {
                entity.inventory.get(i).shrink(1);
            }
        }

        entity.beerType = BeerTypeMapperUtil.getBeerType(holder.value().getResultItem().getItem());

        int fullMugs = entity.waterLevel / BEER_PER_MUG;
        entity.beerLevel = fullMugs * BEER_PER_MUG;
        entity.waterLevel = 0;
        entity.resetProgress();

        setChanged(level, pos, state);
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
