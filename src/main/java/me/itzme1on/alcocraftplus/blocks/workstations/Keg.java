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
import net.minecraft.world.level.LevelAccessor;
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
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public Keg(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new KegEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState oldState, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof KegEntity)
                ((KegEntity) entity).drops();
        }

        super.onRemove(oldState, world, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!world.isClientSide()) {
            BlockEntity entity = world.getBlockEntity(pos);
            ItemStack itemStack = player.getItemInHand(hand);

            if (entity instanceof KegEntity) {
                if (itemStack.is(Items.WATER_BUCKET) && ((KegEntity) entity).waterLevel < ((KegEntity) entity).maxWaterLevel
                        && ((KegEntity) entity).beerLevel == 0) {
                    ((KegEntity) entity).waterLevel = ((KegEntity) entity).waterLevel + 8;
                    world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1f, 1f);

                    if (!player.isCreative())
                        itemStack.shrink(1);

                    if (itemStack.isEmpty())
                        player.setItemInHand(hand, new ItemStack(Items.BUCKET));

                    world.gameEvent(player, GameEvent.FLUID_PLACE, pos);
                }

                else if (itemStack.is(AlcoItems.MUG_EMPTY.get()) && ((KegEntity) entity).beerLevel != 0) {

                    if (!player.isCreative())
                        itemStack.shrink(1);

                    int beer = ((KegEntity) entity).beerType;

                    switch (beer) {
                        case 1: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_SUN_PALE_ALE.get()), false);
                            break;
                        }

                        case 2: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_DIGGER_BITTER.get()), false);
                            break;
                        }

                        case 3: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_NETHER_PORTER.get()), false);
                            break;
                        }

                        case 4: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_WITHER_STOUT.get()), false);
                            break;
                        }

                        case 5: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_MAGNET_PILSNER.get()), false);
                            break;
                        }

                        case 6: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_DROWNED_ALE.get()), false);
                            break;
                        }

                        case 7: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_NIGHT_RAUCH.get()), false);
                            break;
                        }

                        case 8: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_ICE_BEER.get()), false);
                            break;
                        }

                        case 9: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_KVASS.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_KVASS.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_KVASS.get()), false);
                            break;
                        }

                        case 10: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_LEPRECHAUN_CIDER.get()), false);
                            break;
                        }

                        case 11: {
                            if (itemStack.isEmpty())
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get()));

                            else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get())))
                                player.drop(new ItemStack(AlcoItems.MUG_OF_CHORUS_ALE.get()), false);
                            break;
                        }

                        case 12: {
                            if (itemStack.isEmpty()) {
                                player.setItemInHand(hand, new ItemStack(AlcoItems.MUG_OF_NETHER_STAR_LAGER.get()));
                            } else if (!player.getInventory().add(new ItemStack(AlcoItems.MUG_OF_NETHER_STAR_LAGER.get()))) {
                                player.drop(new ItemStack(AlcoItems.MUG_OF_NETHER_STAR_LAGER.get()), false);
                            }

                        }
                    }

                    ((KegEntity) entity).beerLevel = ((KegEntity) entity).beerLevel - 2;

                } else {
                    world.playSound(null, pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1f, 0.01f);
                    world.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.3f, 0.7f);
                    NetworkHooks.openGui(((ServerPlayer) player), (KegEntity) entity, pos);
                }
            } else {
                throw new IllegalStateException("No beer today :(");
            }
        }

        return InteractionResult.sidedSuccess(world.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> entityType) {
        return createTickerHelper(entityType, AlcoBlockEntities.KEG_ENTITY.get(), KegEntity::tick);
    }
}
