package me.itzme1on.alcocraftplus.events;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.loot.DungeonChests;
import me.itzme1on.alcocraftplus.loot.HopSeeds;
import me.itzme1on.alcocraftplus.particles.AlcoParticles;
import me.itzme1on.alcocraftplus.particles.YellowBubbleParticles;
import me.itzme1on.alcocraftplus.recipes.KegRecipe;
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

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlcoCraftEvents {
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
                new HopSeeds.Serializer().setRegistryName
                        (new ResourceLocation(AlcoCraftPlus.MOD_ID,"hop_seeds"))
        );

        event.getRegistry().register(
                new DungeonChests.Serializer().setRegistryName
                        (new ResourceLocation(AlcoCraftPlus.MOD_ID,"add_item"))
        );
    }
}
