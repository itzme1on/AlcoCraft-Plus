package me.itzme1on.alcocraftplus.neoforge.registrations;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = AlcoCraftPlus.MOD_ID)
public class BuiltinPacksRegistration {
    private BuiltinPacksRegistration() {
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;

        event.addPackFinders(
                IdentifierUtil.of("resourcepacks/3d_models"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.alcocraftplus.3d_models_pack.title"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }
}
