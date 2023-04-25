package me.itzme1on.alcocraftplus;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import me.itzme1on.alcocraftplus.blocks.AlcoBlocks;
import me.itzme1on.alcocraftplus.effects.AlcoEffects;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import me.itzme1on.alcocraftplus.particles.AlcoParticles;
import me.itzme1on.alcocraftplus.trades.AlcoTrades;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;

public class AlcoCraftPlus {
    public static final String MOD_ID = "alcocraftplus";

    public static void init() {
        AlcoEffects.init();
        AlcoBlocks.init();
        AlcoItems.init();
        AlcoParticles.init();
        AlcoTrades.init();

        EnvExecutor.runInEnv(Env.CLIENT, () -> AlcoCraftPlus.Client::initClient);
    }

    @Environment(EnvType.CLIENT)
    public static class Client {
        @Environment(EnvType.CLIENT)
        public static void initClient() {
            RenderTypeRegistry.register(RenderType.cutout(), AlcoBlocks.HOP.get());
            RenderTypeRegistry.register(RenderType.cutout(), AlcoBlocks.HOP_PLANT.get());
            RenderTypeRegistry.register(RenderType.cutout(), AlcoBlocks.MUG.get());
            RenderTypeRegistry.register(RenderType.cutout(), AlcoBlocks.SUN_PALE_ALE.get());
        }
    }
}