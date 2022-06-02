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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlcoCraft.MOD_ID)
public class AlcoCraft {
    public static final String MOD_ID = "alcocraft";

    private static final Logger LOGGER = LogUtils.getLogger();

    public AlcoCraft() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AlcoBlocks.register(eventBus);
        AlcoItems.register(eventBus);
        AlcoBlockEntities.register(eventBus);
        AlcoMenuTypes.register(eventBus);
        AlcoRecipes.register(eventBus);
        AlcoEffects.register(eventBus);
        AlcoParticles.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.SPRUCE_KEG.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.HOP_PLANT_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.SPRUCE_MUG_BLOCK_SUN_PALE_ALE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AlcoBlocks.SPRUCE_MUG_BLOCK_EMPTY.get(), RenderType.cutout());

        MenuScreens.register(AlcoMenuTypes.KEG_MENU.get(), KegScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
