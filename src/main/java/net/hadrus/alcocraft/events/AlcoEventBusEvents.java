package net.hadrus.alcocraft.events;

import net.hadrus.alcocraft.AlcoCraft;
import net.hadrus.alcocraft.loot.DungeonChests;
import net.hadrus.alcocraft.loot.HopSeedsFromGrass;
import net.hadrus.alcocraft.particles.AlcoParticles;
import net.hadrus.alcocraft.particles.YellowBubbleParticles;
import net.hadrus.alcocraft.recipes.KegRecipe;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = AlcoCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlcoEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, (helper) -> {
            event.getForgeRegistry().register(KegRecipe.Type.ID, KegRecipe.Type.INSTANCE);
        });
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(AlcoParticles.YELLOW_BUBBLES.get(), YellowBubbleParticles.Provider::new);
    }

    @SubscribeEvent
    public static void registerModifierSerializers(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, (helper) -> {
            event.getForgeRegistry().register("hop_seeds_from_grass", HopSeedsFromGrass.CODEC);
            event.getForgeRegistry().register("add_item", DungeonChests.CODEC);
        });
    }
}