package me.itzme1on.alcocraftplus.core.effects;

import me.itzme1on.alcocraftplus.core.mixin.AttractTracked;
import me.itzme1on.alcocraftplus.core.registries.EffectsRegistry;
import me.itzme1on.alcocraftplus.core.utils.ColorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AttractEffect extends MobEffect {
    private static final long PARTICLE_INTERVAL_TICKS = 20L;
    private static final double PARTICLES_PER_BLOCK = 2.0D;
    private static final DustParticleOptions DUST_COLOR = new DustParticleOptions(
            ColorUtil.getColorFromRGB(170, 14, 1),
            0.5f
    );

    private static final double SEARCH_RADIUS_BLOCKS = 15.0D;
    private static final double ATTRACT_RING_RADIUS_BLOCKS = 1.0D;

    private static final double EPSILON = 1.0E-4D;
    private static final double MAX_VERTICAL_DELTA = 0.35D;

    private static Holder<MobEffect> ATTRACT_HOLDER;

    public AttractEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static Holder<MobEffect> attractHolder() {
        if (ATTRACT_HOLDER == null)
            ATTRACT_HOLDER = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(EffectsRegistry.ATTRACT.get());

        return ATTRACT_HOLDER;
    }

    private static Set<ItemEntity> selectParticleSources(List<ItemEntity> nearbyItems,
                                                         List<LivingEntity> attractEffectHolders,
                                                         LivingEntity contextHolder) {
        Map<BlockPos, ItemEntity> bestItemByBlock = new HashMap<>();
        Map<BlockPos, Double> bestDist2ByBlock = new HashMap<>();

        if (attractEffectHolders.isEmpty()) return Collections.emptySet();

        for (ItemEntity item : nearbyItems) {
            if (!item.isAlive() || item.tickCount < 20) continue;

            LivingEntity nearest = findNearest(attractEffectHolders, item);

            if (nearest == null || nearest.getId() != contextHolder.getId()) continue;

            double distanceToItem = squaredDistanceFeetToItem(nearest, item);

            BlockPos key = item.blockPosition();

            Double best = bestDist2ByBlock.get(key);

            if (best == null || distanceToItem < best) {
                bestDist2ByBlock.put(key, distanceToItem);

                bestItemByBlock.put(key, item);
            }
        }

        return new HashSet<>(bestItemByBlock.values());
    }

    private static LivingEntity findNearest(List<LivingEntity> holders, ItemEntity item) {
        LivingEntity nearest = null;
        double bestDist2 = Double.MAX_VALUE;
        for (LivingEntity h : holders) {
            double d2 = squaredDistanceFeetToItem(h, item);
            if (d2 < bestDist2) {
                bestDist2 = d2;
                nearest = h;
            }
        }
        return nearest;
    }

    private static Vec3 itemCenter(ItemEntity item) {
        return new Vec3(item.getX(), item.getY() + item.getBbHeight() * 0.5D, item.getZ());
    }

    private static Vec3 entityFeet(LivingEntity e) {
        return new Vec3(e.getX(), e.getY() + 0.05D, e.getZ());
    }

    private static Vec3 entityWaist(LivingEntity e) {
        return new Vec3(e.getX(), e.getY() + e.getBbHeight() * 0.5D, e.getZ());
    }

    private static double squaredDistanceFeetToItem(LivingEntity e, ItemEntity item) {
        Vec3 feet = entityFeet(e);
        Vec3 center = itemCenter(item);

        double dx = feet.x - center.x;
        double dy = feet.y - center.y;
        double dz = feet.z - center.z;

        return dx * dx + dy * dy + dz * dz;
    }

    private static Vec3 sphericalTargetAroundFeet(ItemEntity item, LivingEntity target) {
        Vec3 from = itemCenter(item);
        Vec3 to = entityFeet(target);

        return computeRingPointTowardTarget(from, to);
    }

    private static Vec3 sphericalTargetAroundWaist(ItemEntity item, LivingEntity target) {
        Vec3 from = itemCenter(item);
        Vec3 to = entityWaist(target);

        return computeRingPointTowardTarget(from, to);
    }

    @NotNull
    private static Vec3 computeRingPointTowardTarget(Vec3 from, Vec3 to) {
        Vec3 diff = to.subtract(from);

        double len = Math.max(diff.length(), EPSILON);

        Vec3 unit = diff.scale(1.0D / len);

        return new Vec3(
                to.x - unit.x * ATTRACT_RING_RADIUS_BLOCKS,
                to.y - unit.y * ATTRACT_RING_RADIUS_BLOCKS,
                to.z - unit.z * ATTRACT_RING_RADIUS_BLOCKS
        );
    }

    private static void spawnDustLine(ServerLevel level, Vec3 from, Vec3 to) {
        double distance = from.distanceTo(to);

        int points = Mth.clamp((int) Math.floor(distance * PARTICLES_PER_BLOCK) + 1, 1, 200);

        if (points <= 1 || distance < 1.0E-6D) return;

        double stepX = (to.x - from.x) / (points - 1);
        double stepY = (to.y - from.y) / (points - 1);
        double stepZ = (to.z - from.z) / (points - 1);

        double x = from.x;
        double y = from.y;
        double z = from.z;

        for (int i = 0; i < points; i++) {
            level.sendParticles(DUST_COLOR, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            x += stepX;
            y += stepY;
            z += stepZ;
        }
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int i) {
        if (level.isClientSide) return true;

        var searchBox = entity.getBoundingBox().inflate(SEARCH_RADIUS_BLOCKS);

        List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, searchBox);

        List<LivingEntity> attractEffectHolders = level.getEntitiesOfClass(LivingEntity.class, searchBox)
                .stream()
                .filter(e -> e.isAlive() &&
                        e.hasEffect(attractHolder()))
                .toList();

        if (attractEffectHolders.isEmpty() || nearbyItems.isEmpty()) return true;

        Set<ItemEntity> particleSources = selectParticleSources(nearbyItems, attractEffectHolders, entity);

        Map<Integer, Integer> amplifierByHolderId = new HashMap<>();

        for (LivingEntity holder : attractEffectHolders) {
            MobEffectInstance inst = holder.getEffect(attractHolder());

            amplifierByHolderId.put(holder.getId(), inst == null ? 0 : inst.getAmplifier());
        }

        for (ItemEntity item : nearbyItems) {
            if (!item.isAlive()) continue;

            if (item.tickCount < 20) continue;

            LivingEntity nearestHolder = findNearest(attractEffectHolders, item);

            if (item instanceof AttractTracked trackedId)
                trackedId.alcocraftplus$setAttractorId(nearestHolder != null ? nearestHolder.getId() : 0);

            if (nearestHolder == null || entity.getId() != nearestHolder.getId()) continue;

            Vec3 itemCenter = itemCenter(item);
            Vec3 holderFeet = entityFeet(nearestHolder);
            Vec3 toFeet = holderFeet.subtract(itemCenter);

            double distanceToFeet = Math.max(toFeet.length(), EPSILON);

            Vec3 velocity = item.getDeltaMovement();
            Vec3 updatedVelocity = velocity;

            if (distanceToFeet <= ATTRACT_RING_RADIUS_BLOCKS) {
                if (level instanceof ServerLevel serverLevel
                        && particleSources.contains(item)
                        && (serverLevel.getGameTime() % PARTICLE_INTERVAL_TICKS) == 0L) {
                    Vec3 waistTarget = sphericalTargetAroundWaist(item, nearestHolder);
                    spawnDustLine(serverLevel, itemCenter, waistTarget);
                }

                continue;
            }

            Vec3 weightedDirectionSum = Vec3.ZERO;
            double totalWeight = 0.0D;

            for (LivingEntity holder : attractEffectHolders) {
                Vec3 targetPoint = sphericalTargetAroundFeet(item, holder);
                Vec3 toTarget = targetPoint.subtract(itemCenter);

                double toTargetLength = toTarget.length();

                if (toTargetLength <= 1.0E-6D) continue;

                double feetDistance = Math.sqrt(squaredDistanceFeetToItem(holder, item));

                if (feetDistance > SEARCH_RADIUS_BLOCKS) continue;

                double closenessNorm = (SEARCH_RADIUS_BLOCKS - Math.min(feetDistance, SEARCH_RADIUS_BLOCKS)) / SEARCH_RADIUS_BLOCKS;

                double weight = closenessNorm * closenessNorm;

                totalWeight += weight;

                weightedDirectionSum = weightedDirectionSum.add(toTarget.scale((1.0D / toTargetLength) * weight));
            }

            if (weightedDirectionSum.lengthSqr() > 1.0E-6D) {
                Vec3 direction = weightedDirectionSum.normalize();

                double weightFactor = Math.min(totalWeight, 1.0D);

                int nearestAmplifier = amplifierByHolderId.getOrDefault(nearestHolder.getId(), 0);

                double acceleration = 0.08D + 0.02D * Math.max(0, nearestAmplifier);

                Vec3 deltaV = direction.scale(weightFactor * acceleration);

                if (deltaV.y > MAX_VERTICAL_DELTA)
                    deltaV = new Vec3(deltaV.x, MAX_VERTICAL_DELTA, deltaV.z);

                if (deltaV.y < -MAX_VERTICAL_DELTA)
                    deltaV = new Vec3(deltaV.x, -MAX_VERTICAL_DELTA, deltaV.z);

                updatedVelocity = velocity.add(deltaV);
            }

            if (updatedVelocity.y > MAX_VERTICAL_DELTA)
                updatedVelocity = new Vec3(updatedVelocity.x, MAX_VERTICAL_DELTA, updatedVelocity.z);

            if (updatedVelocity.y < -MAX_VERTICAL_DELTA)
                updatedVelocity = new Vec3(updatedVelocity.x, -MAX_VERTICAL_DELTA, updatedVelocity.z);

            item.setDeltaMovement(updatedVelocity);

            if (item instanceof AttractTracked tracked) {
                Vec3 appliedAcceleration = updatedVelocity.subtract(velocity);

                tracked.alcocraftplus$setAttractAccel(appliedAcceleration);
                tracked.alcocraftplus$setAttractTtl(2);
            }

            if (level instanceof ServerLevel serverLevel &&
                    particleSources.contains(item) &&
                    (serverLevel.getGameTime() % PARTICLE_INTERVAL_TICKS) == 0L) {
                Vec3 toWaist = sphericalTargetAroundWaist(item, nearestHolder);
                spawnDustLine(serverLevel, itemCenter, toWaist);
            }
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
