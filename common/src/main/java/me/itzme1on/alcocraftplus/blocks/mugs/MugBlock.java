package me.itzme1on.alcocraftplus.blocks.mugs;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MugBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(4.0d, 0.0d, 4.0d, 12.0d, 8.0d, 12.0d);

    public MugBlock() {
        super(BlockBehaviour.Properties
                .copy(Blocks.BARREL)
                .instabreak()
                .noOcclusion());
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter,
                               @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        if (Minecraft.getInstance().options.keyShift.isDown())
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        else return null;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader world, BlockPos pos) {
        return canSupportCenter(world, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState state2,
                                  @NotNull LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull BlockPos pos2) {
        return direction == Direction.DOWN && !this.canSurvive(state, levelAccessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state2, levelAccessor, pos, pos2);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, true);
    }
}
