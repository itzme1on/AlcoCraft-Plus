package me.itzme1on.alcocraftplus.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FreezeEffect extends MobEffect {
    protected FreezeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isOnGround()) {
            BlockState state = Blocks.FROSTED_ICE.defaultBlockState();
            BlockPos pos = entity.blockPosition();

            int levelConflicting = 1;

            float f = Math.min(16, 2 + levelConflicting);

            BlockPos.MutableBlockPos blockPos$mutableBlockPos = new BlockPos.MutableBlockPos();

            for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-f, -1.0D, -f), pos.offset(f, -1.0D, f))) {
                if (blockpos.closerToCenterThan(entity.position(), (double)f)) {
                    blockPos$mutableBlockPos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockState1 = entity.level.getBlockState(blockPos$mutableBlockPos);
                    if (blockState1.isAir()) {
                        BlockState blockState2 = entity.level.getBlockState(blockpos);
                        boolean isFull = blockState2.getBlock() == Blocks.WATER && blockState2.getValue(LiquidBlock.LEVEL) == 0;
                        if (blockState2.getMaterial() == Material.WATER &&
                                isFull &&
                                state.canSurvive(entity.level, blockpos) &&
                                entity.level.isUnobstructed(state, blockpos, CollisionContext.empty()) &&
                                !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(entity, net.minecraftforge.common.util.BlockSnapshot.create(entity.level.dimension(), entity.level, blockpos), net.minecraft.core.Direction.UP)) {
                            entity.level.setBlockAndUpdate(blockpos, state);
                            entity.level.scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(entity.getRandom(), 60, 120));
                        }
                    }
                }
            }
        }

        Level world = entity.level;

        for (Entity e : world.getEntities(entity, new AABB(entity.blockPosition()).inflate(5))) {
            if (e instanceof LivingEntity livingEntity)
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 2));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
