package me.itzme1on.alcocraftplus.core.registries;

import dev.architectury.registry.menu.MenuRegistry; // Architectury's MenuRegistry helper
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import me.itzme1on.alcocraftplus.core.menu.KegMenuCommon; // Import your common menu
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MenuRegistry {

    // Line 15: DeferredRegister for MenuType
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.MENU);

    // Line 19: RegistrySupplier for the Keg Menu
    public static final RegistrySupplier<MenuType<KegMenuCommon>> KEG_MENU =
            MENUS.register("keg_menu", () -> MenuRegistry.commonMenuType(KegMenuCommon::new));
    // Uses Architectury's helper to create a MenuType that works on both platforms
    // KegMenuCommon::new refers to the constructor that takes (int windowId, Inventory playerInventory, FriendlyByteBuf extraData)
    // Architectury handles mapping this correctly to the server-side constructor with the BlockEntity

    // Line 26: Initialization method
    public static void init() {
        MENUS.register(); // Registers all menus defined above
        AlcoCraftPlus.LOGGER.info("Registering Mod Menus for " + AlcoCraftPlus.MOD_ID);
    }
}