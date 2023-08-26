package me.itzme1on.alcocraftplus.blocks.workstations;

import me.itzme1on.alcocraftplus.blocks.AlcoBlockEntities;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class Keg extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public Keg(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return this.defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        Rotation rotation = mirror.getRotation(blockState.getValue(FACING));

        return this.defaultBlockState().setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KegEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (blockState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof KegEntity) {
                ((KegEntity) blockEntity).drops();
            }
        }

        super.onRemove(blockState, level, blockPos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            ItemStack itemStack = pPlayer.getItemInHand(pHand);

            if (entity instanceof KegEntity) {
                if (itemStack.is(Items.WATER_BUCKET) && ((KegEntity) entity).waterLevel < ((KegEntity) entity).maxWaterLevel
                        && ((KegEntity) entity).beerLevel == 0) {
                    ((KegEntity) entity).waterLevel = ((KegEntity) entity).waterLevel + 8;
                    pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);

                    if (!pPlayer.isCreative()) {
                        itemStack.shrink(1);
                    }
                    if (itemStack.isEmpty()) {
                        pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                    }

                    pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
                } else if (itemStack.is(AlcoItems.MUG_EMPTY.get()) && ((KegEntity) entity).beerLevel != 0) {

                    if (!pPlayer.isCreative()) {
                        itemStack.shrink(1);
                    }

                    int beer = ((KegEntity) entity).beerType;

                    if (beer == 1) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get()), false);
                        }
                    } else if (beer == 2) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get()), false);
                        }
                    } else if (beer == 3) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get()), false);
                        }
                    } else if (beer == 4) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get()), false);
                        }
                    } else if (beer == 5) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get()), false);
                        }
                    } else if (beer == 6) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get()), false);
                        }
                    } else if (beer == 7) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get()), false);
                        }
                    } else if (beer == 8) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get()), false);
                        }
                    } else if (beer == 9) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_KVASS.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_KVASS.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_KVASS.get()), false);
                        }
                    } else if (beer == 10) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get()), false);
                        }
                    } else if (beer == 11) {
                        if (itemStack.isEmpty()) {
                            pPlayer.setItemInHand(pHand, new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get()));
                        } else if (!pPlayer.getInventory().add(new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get()))) {
                            pPlayer.drop(new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get()), false);
                        }
                    }

                    ((KegEntity) entity).beerLevel = ((KegEntity) entity).beerLevel - 2;

                } else {
                    pLevel.playSound(null, pPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1f, 0.01f);
                    pLevel.playSound(null, pPos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.3f, 0.7f);
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (KegEntity) entity, pPos);
                }
            } else {
                throw new IllegalStateException("No beer today :(");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, AlcoBlockEntities.KEG_ENTITY.get(),
                KegEntity::tick);
    }
}