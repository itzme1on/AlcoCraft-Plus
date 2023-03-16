package me.itzme1on.alcocraftplus.effects;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AlcoEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AlcoCraftPlus.MOD_ID);

    public static final RegistryObject<MobEffect> ATTRACT = EFFECTS.register("attract",
            () -> new AttractEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(170, 14, 1)));

    public static final RegistryObject<MobEffect> FLIGHT = EFFECTS.register("flight",
            () -> new FlightEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(199, 183, 255)));

    public static final RegistryObject<MobEffect> FREEZE = EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(143, 181, 246)));

    public static final RegistryObject<MobEffect> PHANTOM = EFFECTS.register("phantom",
            () -> new PhantomEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(58, 70, 123)));

    public static final RegistryObject<MobEffect> WITHER = EFFECTS.register("wither",
            () -> new WitherEffect(MobEffectCategory.BENEFICIAL, rawColorFromRGB(29, 3, 3)));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }

    public static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);

        return rgb;
    }
}
