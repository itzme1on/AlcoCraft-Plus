package me.itzme1on.alcocraftplus;

import me.itzme1on.alcocraftplus.blocks.AlcoBlockEntities;
import me.itzme1on.alcocraftplus.blocks.AlcoBlocks;
import me.itzme1on.alcocraftplus.effects.AlcoEffects;
import me.itzme1on.alcocraftplus.gui.AlcoMenuTypes;
import me.itzme1on.alcocraftplus.gui.KegScreen;
import me.itzme1on.alcocraftplus.items.AlcoItems;
import me.itzme1on.alcocraftplus.particles.AlcoParticles;
import me.itzme1on.alcocraftplus.recipes.AlcoRecipes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AlcoCraftPlus.MOD_ID)
public class AlcoCraftPlus {
    public static final String MOD_ID = "alcocraftplus";
    private static final Logger LOGGER = LogManager.getLogger();

    public AlcoCraftPlus() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AlcoBlocks.register(eventBus);
        AlcoItems.register(eventBus);
        AlcoBlockEntities.register(eventBus);
        AlcoMenuTypes.register(eventBus);
        AlcoRecipes.register(eventBus);
        AlcoParticles.register(eventBus);
        AlcoEffects.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.KEG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.MUG_EMPTY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.MUG_OF_SUN_PALE_ALE.get(), RenderType.cutout());

        MenuScreens.register(AlcoMenuTypes.KEG_MENU.get(), KegScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Hello from preinit!");
    }
}
