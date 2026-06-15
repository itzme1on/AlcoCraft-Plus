package me.itzme1on.alcocraftplus.core.blocks.keg;

import me.itzme1on.alcocraftplus.core.registries.BlockEntitiesRegistry;
import me.itzme1on.alcocraftplus.core.registries.ItemsRegistry;
import me.itzme1on.alcocraftplus.core.utils.BeerTypeMapperUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Keg extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape KEG_SHAPE = Shapes.or(
            Block.box(7.0D, 16.0D, 7.0D, 9.0D, 17.0D, 9.0D),
            Block.box(3.0D, 14.0D, 1.0D, 13.0D, 15.0D, 2.0D),
            Block.box(2.0D, 4.0D, 1.0D, 14.0D, 14.0D, 2.0D),
            Block.box(3.0D, 3.0D, 1.0D, 13.0D, 4.0D, 2.0D),
            Block.box(2.0D, 4.0D, 14.0D, 14.0D, 14.0D, 15.0D),
            Block.box(3.0D, 3.0D, 14.0D, 13.0D, 4.0D, 15.0D),
            Block.box(3.0D, 14.0D, 14.0D, 13.0D, 15.0D, 15.0D),
            Block.box(14.0D, 4.0D, 0.0D, 15.0D, 14.0D, 16.0D),
            Block.box(3.0D, 15.0D, 0.0D, 13.0D, 16.0D, 16.0D),
            Block.box(13.0D, 14.0D, 0.0D, 14.0D, 15.0D, 16.0D),
            Block.box(1.0D, 4.0D, 0.0D, 2.0D, 14.0D, 16.0D),
            Block.box(2.0D, 14.0D, 0.0D, 3.0D, 15.0D, 16.0D),
            Block.box(2.0D, 3.0D, 0.0D, 3.0D, 4.0D, 16.0D),
            Block.box(3.0D, 2.0D, 0.0D, 13.0D, 3.0D, 16.0D),
            Block.box(13.0D, 3.0D, 0.0D, 14.0D, 4.0D, 16.0D),
            Block.box(7.0D, 5.0D, 0.0D, 9.0D, 7.0D, 1.0D),
            Block.box(3.0D, 0.0D, 2.0D, 5.0D, 1.0D, 4.0D),
            Block.box(11.0D, 0.0D, 2.0D, 13.0D, 1.0D, 4.0D),
            Block.box(3.0D, 1.0D, 2.0D, 13.0D, 2.0D, 4.0D),
            Block.box(3.0D, 0.0D, 12.0D, 5.0D, 1.0D, 14.0D),
            Block.box(11.0D, 0.0D, 12.0D, 13.0D, 1.0D, 14.0D),
            Block.box(3.0D, 1.0D, 12.0D, 13.0D, 2.0D, 14.0D)
    );

    private static final VoxelShape SHAPE_NORTH = KEG_SHAPE;
    private static final VoxelShape SHAPE_EAST = rotateShape(Direction.EAST);
    private static final VoxelShape SHAPE_SOUTH = rotateShape(Direction.SOUTH);
    private static final VoxelShape SHAPE_WEST = rotateShape(Direction.WEST);

    private static VoxelShape rotateShape(Direction to) {
        VoxelShape[] buffer = new VoxelShape[]{Keg.SHAPE_NORTH, Shapes.empty()};

        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;

        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public Keg(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Rotation rotation = mirror.getRotation(state.getValue(FACING));

        return this.defaultBlockState().setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KegEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.sidedSuccess(true);

        BlockEntity entity = level.getBlockEntity(pos);

        if (!(entity instanceof KegEntity keg)) return InteractionResult.PASS;

        ItemStack heldItem = player.getItemInHand(hand);

        if (canPourBeer(heldItem, keg)) return handlePouringBeer(player, hand, keg);

        if (canAddWater(heldItem, keg)) return handleAddingWater(level, pos, player, hand, keg);

        return openKegScreen(state, level, pos, player);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof KegEntity kegEntity)
                kegEntity.getItems().forEach(stack -> {
                    if (!stack.isEmpty()) {
                        popResource(level, pos, stack);
                    }
                });
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntitiesRegistry.KEG_ENTITY.get(), (world, pos, st, entity) -> {
            if (entity != null) entity.tick(world, pos, st, entity);
        });
    }

    private boolean canPourBeer(ItemStack heldItem, KegEntity keg) {
        return heldItem.is(ItemsRegistry.MUG.get()) && keg.beerLevel >= 4;
    }

    private InteractionResult handlePouringBeer(Player player, InteractionHand hand, KegEntity keg) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (!player.isCreative()) heldItem.shrink(1);

        ItemStack beerStack = BeerTypeMapperUtil.getBeerStack(keg.beerType);

        if (!beerStack.isEmpty()) {
            ItemStack pouredBeer = beerStack.copy();
            player.awardStat(Stats.ITEM_USED.get(ItemsRegistry.MUG.get()));
            if (player.getInventory().add(pouredBeer)) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.5F, 1.0F);
            } else {
                player.drop(pouredBeer, false);
            }
        }

        keg.beerLevel -= 4;

        return InteractionResult.SUCCESS;
    }

    private boolean canAddWater(ItemStack heldItem, KegEntity keg) {
        return heldItem.is(Items.WATER_BUCKET) && keg.waterLevel <= keg.maxWaterLevel - 10 && keg.beerLevel == 0;
    }

    private InteractionResult handleAddingWater(Level level, BlockPos pos, Player player, InteractionHand hand, KegEntity keg) {
        keg.waterLevel += 10;

        level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);

        ItemStack heldItem = player.getItemInHand(hand);

        if (!player.isCreative()) {
            heldItem.shrink(1);
            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        }

        player.gameEvent(GameEvent.FLUID_PLACE, player);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult openKegScreen(BlockState state, Level level, BlockPos pos, Player player) {
        MenuProvider screenHandlerFactory = state.getMenuProvider(level, pos);

        if (screenHandlerFactory != null) {
            player.openMenu(screenHandlerFactory);

            level.playSound(null, pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0f, 0.8f);
            level.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0f, 1.0f);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}