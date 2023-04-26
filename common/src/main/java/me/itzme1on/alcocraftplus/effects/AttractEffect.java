package me.itzme1on.alcocraftplus.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AttractEffect extends MobEffect {
    private final int color;

    public AttractEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);

        this.color = color;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level;

        for(Entity e : world.getEntities(entity, new AABB(entity.blockPosition()).inflate(15))){
            if (e instanceof ItemEntity item) {
                Vec3 motion = entity.position().subtract(item.position().add(0, item.getBbHeight() / 2, 0));
                if (Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1)
                    motion = motion.normalize();

                item.setDeltaMovement(motion.scale(1.0));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public int getColor() {
        return this.color;
    }
}
