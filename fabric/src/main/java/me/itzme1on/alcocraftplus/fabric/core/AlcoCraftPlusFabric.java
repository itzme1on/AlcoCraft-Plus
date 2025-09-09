package me.itzme1on.alcocraftplus.fabric.core;

import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

public final class AlcoCraftPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AlcoCraftPlus.init();

        FabricLoader.getInstance().getModContainer(AlcoCraftPlus.MOD_ID).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                    IdentifierUtil.of("3d_models"),
                    modContainer,
                    Component.translatable("pack.alcocraftplus.3d_models_pack.title"),
                    ResourcePackActivationType.NORMAL
            );
        });
    }
}
