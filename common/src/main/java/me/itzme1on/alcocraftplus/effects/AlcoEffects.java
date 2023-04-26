package me.itzme1on.alcocraftplus.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;

public class AlcoEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.MOB_EFFECT_REGISTRY);

    public static final RegistrySupplier<AttractEffect> ATTRACT = EFFECTS.register("attract", () ->
            new AttractEffect(rawColorFromRGB(170, 14, 1)));

    public static final RegistrySupplier<FreezeEffect> FREEZE = EFFECTS.register("freeze", () ->
            new FreezeEffect(rawColorFromRGB(143, 181, 246)));

    public static final RegistrySupplier<PhantomEffect> PHANTOM = EFFECTS.register("phantom", () ->
            new PhantomEffect(rawColorFromRGB(58, 70, 123)));

    public static final RegistrySupplier<WitherEffect> WITHER = EFFECTS.register("wither", () ->
            new WitherEffect(rawColorFromRGB(29, 3, 3)));

    public static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);

        return rgb;
    }

    public static void init() {
        EFFECTS.register();
    }
}
