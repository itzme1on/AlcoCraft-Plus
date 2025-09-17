package me.itzme1on.alcocraftplus.fabric.registrations;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

public final class FabricBuiltinPackRegistration {
    private FabricBuiltinPackRegistration() {
    }

    public static void registerBuiltinPacks() {
        FabricLoader.getInstance().getModContainer(AlcoCraftPlus.MOD_ID).ifPresent(modContainer ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        IdentifierUtil.create("3d_models"),
                        modContainer,
                        Component.translatable("pack.alcocraftplus.3d_models_pack.title"),
                        ResourcePackActivationType.NORMAL
                )
        );
    }
}
