package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.client.gui.handler.KegGuiHandler;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ScreenHandlerRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<KegGuiHandler>> KEG_MENU = register("keg_menu", () -> new MenuType<>(KegGuiHandler::new));

    public static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String name, Supplier<MenuType<T>> menuType) {
        return MENUS.register(IdentifierUtil.create(name), menuType);
    }

    public static void register() {
        MENUS.register();
    }
}
