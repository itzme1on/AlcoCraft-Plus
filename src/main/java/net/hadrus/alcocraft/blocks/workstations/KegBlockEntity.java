package net.hadrus.alcocraft.blocks.workstations;

import com.mojang.logging.LogUtils;
import net.hadrus.alcocraft.blocks.AlcoBlockEntities;
import net.hadrus.alcocraft.gui.KegMenu;
import net.hadrus.alcocraft.items.AlcoItems;
import net.hadrus.alcocraft.recipes.KegRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.Random;

public class KegBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 18000;
    public int waterLevel = 0;
    public int maxWaterLevel = 40;
    public int beerLevel = 0;
    public int beerType = 0;

    public KegBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AlcoBlockEntities.KEG_ENTITY.get(), blockPos, blockState);

        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return KegBlockEntity.this.progress;
                    case 1: return KegBlockEntity.this.maxProgress;
                    case 2: return KegBlockEntity.this.waterLevel;
                    case 3: return KegBlockEntity.this.beerLevel;
                    case 4: return KegBlockEntity.this.beerType;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: KegBlockEntity.this.progress = value; break;
                    case 1: KegBlockEntity.this.maxProgress = value; break;
                    case 2: KegBlockEntity.this.waterLevel = value; break;
                    case 3: KegBlockEntity.this.beerLevel = value; break;
                    case 4: KegBlockEntity.this.beerType = value; break;
                }
            }

            public int getCount() {
                return 5;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Keg");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new KegMenu(containerId, inventory, this, this.data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("gem_cutting_station.progress", progress);
        tag.putInt("water", waterLevel);
        tag.putInt("beer", beerLevel);
        tag.putInt("beerType", beerType);

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("gem_cutting_station.progress");
        waterLevel = tag.getInt("water");
        beerLevel = tag.getInt("beer");
        beerType = tag.getInt("beerType");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, KegBlockEntity pBlockEntity) {
        Random random = pLevel.random;

        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);

            if (random.nextFloat() < 0.005f) {
                pLevel.playSound(null, pPos, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.1f, 0.5f);
            }

            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }

    }

    private static boolean hasRecipe(KegBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KegRecipe> match = level.getRecipeManager()
                .getRecipeFor(KegRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && hasWater(entity) && !hasBeer(entity);
    }

    private static boolean hasWater(KegBlockEntity entity) {
        if (entity.waterLevel != 0) {
            return true;
        } else
        {
            return false;
        }
    }

    private static boolean hasBeer(KegBlockEntity entity) {
        if (entity.beerLevel != 0) {
            return true;
        } else
        {
            return false;
        }
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private static void craftItem(KegBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KegRecipe> match = level.getRecipeManager()
                .getRecipeFor(KegRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(2,1, false);
            entity.itemHandler.extractItem(3,1, false);

            if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_SUN_PALE_ALE.get())) {
                entity.beerType = 1;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_DIGGER_BITTER.get())) {
                entity.beerType = 2;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_NETHER_PORTER.get())) {
                entity.beerType = 3;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_WITHER_STOUT.get())) {
                entity.beerType = 4;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_MAGNET_PILSNER.get())) {
                entity.beerType = 5;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_DROWNED_ALE.get())) {
                entity.beerType = 6;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_NIGHT_RAUCH.get())) {
                entity.beerType = 7;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_ICE_BEER.get())) {
                entity.beerType = 8;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_KVASS.get())) {
                entity.beerType = 9;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_LEPRECHAUN_CIDER.get())) {
                entity.beerType = 10;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_CHORUS_ALE.get())) {
                entity.beerType = 11;
            } else if (match.get().getResultItem().getItem().equals(AlcoItems.SPRUCE_MUG_NETHER_STAR_LAGER.get())) {
                entity.beerType = 12;
            }

            entity.beerLevel = entity.waterLevel;
            entity.waterLevel = 0;

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
