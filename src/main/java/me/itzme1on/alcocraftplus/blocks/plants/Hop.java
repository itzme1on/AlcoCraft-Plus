package me.itzme1on.alcocraftplus.blocks.plants;

import me.itzme1on.alcocraftplus.blocks.AlcoBlocks;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class Hop extends CaveVinesBlock {

    public Hop(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        if (random.nextDouble() < 0.1D)
            this.performBonemeal(world, random, pos, state);
    }

    @Override
    protected Block getBodyBlock() {
        return AlcoBlocks.HOP_PLANT.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(AlcoItems.HOP.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult result) {
        if (state.getValue(BERRIES)) {
            Block.popResource(world, pos, new ItemStack(AlcoItems.HOP.get(), 1));
            float f = Mth.randomBetween(world.random, 0.8F, 1.2F);

            world.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            world.setBlock(pos, state.setValue(BERRIES, Boolean.FALSE), 2);

            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }
}