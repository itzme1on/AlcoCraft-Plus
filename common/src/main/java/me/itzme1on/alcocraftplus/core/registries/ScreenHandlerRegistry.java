package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ScreenHandlerRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.MENU);

    private ScreenHandlerRegistry() {
    }

    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType) {
        return MENUS.register(name, menuType);
    }

    public static void register() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<KegGuiHandler>> KEG_MENU = register("keg_menu", () -> new MenuType<>(KegGuiHandler::new, FeatureFlags.VANILLA_SET));
}
