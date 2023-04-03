package net.hadrus.alcocraft.blocks.plants;

import net.hadrus.alcocraft.blocks.AlcoBlocks;
import net.hadrus.alcocraft.items.AlcoItems;
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
import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Random;

public class HopPlantBlock extends CaveVinesPlantBlock {

    public HopPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock)AlcoBlocks.HOP_BLOCK.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(AlcoItems.HOP.get());
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextDouble() < 0.1D){
            this.performBonemeal(pLevel, pRandom, pPos, pState);
        }
    }

    @Override
    public InteractionResult use(BlockState p_152954_, Level p_152955_, BlockPos p_152956_, Player player,
                                 InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (p_152954_.getValue(BERRIES)) {
            Block.popResource(p_152955_, p_152956_, new ItemStack(AlcoItems.HOP.get(), 1));
            float f = Mth.randomBetween(p_152955_.random, 0.8F, 1.2F);
            p_152955_.playSound((Player)null, p_152956_, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
            p_152955_.setBlock(p_152956_, p_152954_.setValue(BERRIES, Boolean.valueOf(false)), 2);
            return InteractionResult.sidedSuccess(p_152955_.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }
}