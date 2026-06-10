package me.itzme1on.alcocraftplus.fabric.mixin;

import me.itzme1on.alcocraftplus.core.mixin.AttractTracked;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements AttractTracked {
    @Unique
    private static final EntityDataAccessor<Integer> ATTRACTOR_ID =
            SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Float> ATTRACT_ACCELERATION_X =
            SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.FLOAT);
    @Unique
    private static final EntityDataAccessor<Float> ATTRACT_ACCELERATION_Y =
            SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.FLOAT);
    @Unique
    private static final EntityDataAccessor<Float> ATTRACT_ACCELERATION_Z =
            SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.FLOAT);
    @Unique
    private static final EntityDataAccessor<Integer> ATTRACT_TTL =
            SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.INT);

    protected ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void alcocraftplus$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(ATTRACTOR_ID, 0);
        builder.define(ATTRACT_ACCELERATION_X, 0.0f);
        builder.define(ATTRACT_ACCELERATION_Y, 0.0f);
        builder.define(ATTRACT_ACCELERATION_Z, 0.0f);
        builder.define(ATTRACT_TTL, 0);
    }

    @Override
    public int alcocraftplus$getAttractorId() {
        return this.entityData.get(ATTRACTOR_ID);
    }

    @Override
    public void alcocraftplus$setAttractorId(int id) {
        this.entityData.set(ATTRACTOR_ID, id);
    }

    @Override
    public Vec3 alcocraftplus$getAttractAccel() {
        return new Vec3(
                this.entityData.get(ATTRACT_ACCELERATION_X),
                this.entityData.get(ATTRACT_ACCELERATION_Y),
                this.entityData.get(ATTRACT_ACCELERATION_Z)
        );
    }

    @Override
    public void alcocraftplus$setAttractAccel(Vec3 vec) {
        this.entityData.set(ATTRACT_ACCELERATION_X, (float) vec.x);
        this.entityData.set(ATTRACT_ACCELERATION_Y, (float) vec.y);
        this.entityData.set(ATTRACT_ACCELERATION_Z, (float) vec.z);
    }

    @Override
    public int alcocraftplus$getAttractTtl() {
        return this.entityData.get(ATTRACT_TTL);
    }

    @Override
    public void alcocraftplus$setAttractTtl(int ticks) {
        this.entityData.set(ATTRACT_TTL, ticks);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void alcocraftplus$serverResetAccel(CallbackInfo ci) {
        if (level().isClientSide()) return;

        int ttl = this.alcocraftplus$getAttractTtl();

        if (ttl > 0) this.alcocraftplus$setAttractTtl(ttl - 1);

        if (this.alcocraftplus$getAttractTtl() <= 0) {
            this.alcocraftplus$setAttractAccel(Vec3.ZERO);
            this.alcocraftplus$setAttractorId(0);
        }
    }
}

