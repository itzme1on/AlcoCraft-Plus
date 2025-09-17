package me.itzme1on.alcocraftplus.forge.registrations;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ForgeBuiltinPackRegistration {
    private static final String PACK_NAME = "3d_models";
    private static final String PACK_PATH = "resourcepacks/" + PACK_NAME;
    private static final Component PACK_TITLE = Component.translatable("pack.alcocraftplus.3d_models_pack.title");

    private ForgeBuiltinPackRegistration() {
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        Path packRoot = ModList.get()
                .getModFileById(AlcoCraftPlus.MOD_ID)
                .getFile()
                .findResource(PACK_PATH);

        Pack pack = Pack.readMetaAndCreate(
                PACK_NAME,
                PACK_TITLE,
                false,
                id -> new PathPackResources(id, packRoot, false),
                PackType.CLIENT_RESOURCES,
                Pack.Position.TOP,
                PackSource.BUILT_IN
        );

        if (pack != null) {
            event.addRepositorySource(finder -> finder.accept(pack));
        }
    }
}
