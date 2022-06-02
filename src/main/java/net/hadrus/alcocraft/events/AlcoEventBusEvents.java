package net.hadrus.alcocraft.events;

import net.hadrus.alcocraft.AlcoCraft;
import net.hadrus.alcocraft.loot.DungeonChests;
import net.hadrus.alcocraft.loot.HopSeedsFromGrass;
import net.hadrus.alcocraft.particles.AlcoParticles;
import net.hadrus.alcocraft.particles.YellowBubbleParticles;
import net.hadrus.alcocraft.recipes.KegRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = AlcoCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlcoEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, KegRecipe.Type.ID, KegRecipe.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(AlcoParticles.YELLOW_BUBBLES.get(),
                YellowBubbleParticles.Provider::new);
    }

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>>
                                                           event) {
        event.getRegistry().register(
                new HopSeedsFromGrass.Serializer().setRegistryName
                        (new ResourceLocation(AlcoCraft.MOD_ID,"hop_seeds_from_grass"))
        );

        event.getRegistry().register(
                new DungeonChests.Serializer().setRegistryName
                        (new ResourceLocation(AlcoCraft.MOD_ID,"add_item"))
        );
    }
}
