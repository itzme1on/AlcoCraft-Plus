package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.fluids.*; // Import all fluid classes
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class FluidsRegistry {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.FLUID);

    public static final RegistrySupplier<FlowingFluid> KVASS_STILL = FLUIDS.register("kvass",
            KvassFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> KVASS_FLOWING = FLUIDS.register("kvass_flowing",
            KvassFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> ICE_BEER_STILL = FLUIDS.register("ice_beer",
            IceBeerFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> ICE_BEER_FLOWING = FLUIDS.register("ice_beer_flowing",
            IceBeerFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> CHORUS_ALE_STILL = FLUIDS.register("chorus_ale",
            ChorusAleFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> CHORUS_ALE_FLOWING = FLUIDS.register("chorus_ale_flowing",
            ChorusAleFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> DIGGER_BITTER_STILL = FLUIDS.register("digger_bitter",
            DiggerBitterFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> DIGGER_BITTER_FLOWING = FLUIDS.register("digger_bitter_flowing",
            DiggerBitterFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> DROWNED_ALE_STILL = FLUIDS.register("drowned_ale",
            DrownedAleFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> DROWNED_ALE_FLOWING = FLUIDS.register("drowned_ale_flowing",
            DrownedAleFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> LEPRECHAUN_CIDER_STILL = FLUIDS.register("leprechaun_cider",
            LeprechaunCiderFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> LEPRECHAUN_CIDER_FLOWING = FLUIDS.register("leprechaun_cider_flowing",
            LeprechaunCiderFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> MAGNET_PILSNER_STILL = FLUIDS.register("magnet_pilsner",
            MagnetPilsnerFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> MAGNET_PILSNER_FLOWING = FLUIDS.register("magnet_pilsner_flowing",
            MagnetPilsnerFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> NETHER_PORTER_STILL = FLUIDS.register("nether_porter",
            NetherPorterFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> NETHER_PORTER_FLOWING = FLUIDS.register("nether_porter_flowing",
            NetherPorterFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> NETHER_STAR_LAGER_STILL = FLUIDS.register("nether_star_lager",
            NetherStarLagerFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> NETHER_STAR_LAGER_FLOWING = FLUIDS.register("nether_star_lager_flowing",
            NetherStarLagerFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> NIGHT_RAUCH_STILL = FLUIDS.register("night_rauch",
            NightRauchFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> NIGHT_RAUCH_FLOWING = FLUIDS.register("night_rauch_flowing",
            NightRauchFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> SUN_PALE_ALE_STILL = FLUIDS.register("sun_pale_ale",
            SunPaleAleFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> SUN_PALE_ALE_FLOWING = FLUIDS.register("sun_pale_ale_flowing",
            SunPaleAleFluid.Flowing::new);

    public static final RegistrySupplier<FlowingFluid> WITHER_STOUT_STILL = FLUIDS.register("wither_stout",
            WitherStoutFluid.Still::new);
    public static final RegistrySupplier<FlowingFluid> WITHER_STOUT_FLOWING = FLUIDS.register("wither_stout_flowing",
            WitherStoutFluid.Flowing::new);


    public static void init() {
        FLUIDS.register();
        AlcoCraftPlus.LOGGER.info("Registering Mod Fluids for " + AlcoCraftPlus.MOD_ID);
    }
}