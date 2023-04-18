package net.hadrus.alcocraft;

import com.mojang.logging.LogUtils;
import net.hadrus.alcocraft.blocks.AlcoBlockEntities;
import net.hadrus.alcocraft.blocks.AlcoBlocks;
import net.hadrus.alcocraft.effects.AlcoEffects;
import net.hadrus.alcocraft.gui.AlcoMenuTypes;
import net.hadrus.alcocraft.gui.KegScreen;
import net.hadrus.alcocraft.items.AlcoItems;
import net.hadrus.alcocraft.particles.AlcoParticles;
import net.hadrus.alcocraft.recipes.AlcoRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlcoCraftPlus.MOD_ID)
public class AlcoCraftPlus {
    public static final String MOD_ID = "alcocraftplus";

    private static final Logger LOGGER = LogUtils.getLogger();

    public AlcoCraftPlus() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AlcoBlocks.register(eventBus);
        AlcoItems.register(eventBus);
        AlcoBlockEntities.register(eventBus);
        AlcoMenuTypes.register(eventBus);
        AlcoRecipes.register(eventBus);
        AlcoEffects.register(eventBus);
        AlcoParticles.register(eventBus);

        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.KEG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.MUG_OF_SUN_PALE_ALE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.MUG_EMPTY.get(), RenderType.cutout());

        MenuScreens.register(AlcoMenuTypes.KEG_MENU.get(), KegScreen::new);
    }

}