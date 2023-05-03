package net.hadrus.alcocraft.events;

import net.hadrus.alcocraft.AlcoCraftPlus;
import net.hadrus.alcocraft.loot.DungeonChests;
import net.hadrus.alcocraft.loot.HopSeeds;
import net.hadrus.alcocraft.recipes.KegRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlcoCraftEvents  {
    @SubscribeEvent
    public static void registerRecipeTypes(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, (helper) -> {
            event.getForgeRegistry().register(KegRecipe.Type.ID, KegRecipe.Type.INSTANCE);
        });
    }

    @SubscribeEvent
    public static void registerModifierSerializers(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, (helper) -> {
            event.getForgeRegistry().register("hop_seeds", HopSeeds.CODEC);
            event.getForgeRegistry().register("add_item", DungeonChests.CODEC);
        });
    }
}