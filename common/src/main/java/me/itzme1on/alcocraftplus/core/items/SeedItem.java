package me.itzme1on.alcocraftplus.core.items;

import me.itzme1on.alcocraftplus.core.utils.SidedResultsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SeedItem extends Item {
    private final Block plantBlock;

    public SeedItem(Block plantBlock, Properties properties) {
        super(properties);
        this.plantBlock = plantBlock;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos placePos = clickedPos.above();
        BlockState clickedState = level.getBlockState(clickedPos);

        if (!(clickedState.getBlock() instanceof FarmlandBlock) || !level.getBlockState(placePos).isAir())
            return SidedResultsUtil.pass();

        if (!level.isClientSide()) {
            BlockState plant = this.plantBlock.defaultBlockState();

            if (!plant.canSurvive(level, placePos))
                return SidedResultsUtil.fail();

            level.setBlock(placePos, plant, Block.UPDATE_ALL);
            level.playSound(null, placePos, plant.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

            if (!context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
        }

        return SidedResultsUtil.blockSidedSuccess(level);
    }
}
