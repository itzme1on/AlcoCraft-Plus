package me.itzme1on.alcocraftplus.core.mixin;

import net.minecraft.world.phys.Vec3;

public interface AttractTracked {
    int alcocraftplus$getAttractorId();
    void alcocraftplus$setAttractorId(int id);

    Vec3 alcocraftplus$getAttractAccel();
    void alcocraftplus$setAttractAccel(Vec3 vec);

    int alcocraftplus$getAttractTtl();
    void alcocraftplus$setAttractTtl(int ticks);
}
