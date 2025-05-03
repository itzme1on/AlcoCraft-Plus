package me.itzme1on.alcocraftplus.neoforge.core.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

public class FreezeEffect extends MobEffect {
    public FreezeEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.onGround()) {
            Level level = entity.level();
            BlockPos pos = entity.blockPosition();
            BlockState iceState = Blocks.FROSTED_ICE.defaultBlockState();

            int radius = 2 + amplifier;

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos below = pos.offset(x, -1, z);
                    
                    if (below.distSqr(pos) <= radius * radius) {
                        BlockState blockState = level.getBlockState(below);

                        if (blockState.getBlock() == Blocks.WATER && blockState.getValue(LiquidBlock.LEVEL) == 0)
                            if (level.getBlockState(mutablePos.set(below).above()).isAir())
                                if (iceState.canSurvive(level, below) && level.isUnobstructed(iceState, below, CollisionContext.empty())) {
                                    level.setBlockAndUpdate(below, iceState);

                                    level.scheduleTick(below, Blocks.FROSTED_ICE, Mth.nextInt(entity.getRandom(), 60, 120));
                                }
                    }
                }
            }
        }

        double slownessRadius = 5.0;

        AABB aabb = new AABB(entity.getX() - slownessRadius, entity.getY() - slownessRadius, entity.getZ() - slownessRadius,
                entity.getX() + slownessRadius, entity.getY() + slownessRadius, entity.getZ() + slownessRadius);

        entity.level().getEntities(null, aabb).forEach(nearbyEntity -> {
            if (nearbyEntity != entity && nearbyEntity instanceof LivingEntity)
                if (nearbyEntity instanceof Enemy || (nearbyEntity instanceof Wolf && !((TamableAnimal) nearbyEntity).isTame()))
                    ((LivingEntity) nearbyEntity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2));
        });
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
