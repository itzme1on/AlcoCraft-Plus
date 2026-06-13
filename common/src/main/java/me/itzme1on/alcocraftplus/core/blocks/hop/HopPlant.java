package me.itzme1on.alcocraftplus.core.blocks.hop;

import me.itzme1on.alcocraftplus.core.registries.BlocksRegistry;
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import me.itzme1on.alcocraftplus.core.utils.SidedResultsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class HopPlant extends CaveVinesPlantBlock {
    public HopPlant(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlocksRegistry.HOP.get();
    }

    @Override
    protected @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return new ItemStack(ItemsRegistry.HOP.get());
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextDouble() < 0.1d)
            this.performBonemeal(level, random, pos, state);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult blockHitResult) {
        if (state.getValue(BERRIES)) {
            Block.popResource(level, pos, new ItemStack(ItemsRegistry.HOP.get(), 1));
            float f = Mth.randomBetween(level.random, 0.8f, 1.2f);

            level.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0f, f);
            level.setBlock(pos, state.setValue(BERRIES, Boolean.FALSE), 2);

            return SidedResultsUtil.blockSidedSuccess(level);
        } else {
            return SidedResultsUtil.pass();
        }
    }

    @Override
    protected int getLightBlock(BlockState blockState) {
        return 0;
    }
}
