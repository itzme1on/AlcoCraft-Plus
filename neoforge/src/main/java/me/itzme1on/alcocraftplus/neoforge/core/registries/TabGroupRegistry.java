package me.itzme1on.alcocraftplus.neoforge.core.registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.itzme1on.alcocraftplus.AlcoCraftPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabGroupRegistry {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(AlcoCraftPlus.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ALCOCRAFTPLUS_TAB = TAB.register(
            "alcocraftplus", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
                    .icon(() -> new ItemStack(ItemsRegistry.MUG.get()))
                    .title(Component.translatable("itemGroup.alcocraftplus.tab"))
                    .displayItems(((params, output) -> {
                        output.accept(new ItemStack(ItemsRegistry.HOP.get()));
                        output.accept(new ItemStack(ItemsRegistry.HOP_SEEDS.get()));
                        output.accept(new ItemStack(ItemsRegistry.DRY_SEEDS.get()));
                        output.accept(new ItemStack(BlocksRegistry.KEG.get()));
                        output.accept(new ItemStack(ItemsRegistry.MUG.get()));
                        output.accept(new ItemStack(ItemsRegistry.KVASS.get()));
                        output.accept(new ItemStack(ItemsRegistry.CHORUS_ALE.get()));
                        output.accept(new ItemStack(ItemsRegistry.DIGGER_BITTER.get()));
                        output.accept(new ItemStack(ItemsRegistry.DROWNED_ALE.get()));
                        output.accept(new ItemStack(ItemsRegistry.ICE_BEER.get()));
                        output.accept(new ItemStack(ItemsRegistry.LEPRECHAUN_CIDER.get()));
                        output.accept(new ItemStack(ItemsRegistry.MAGNET_PILSNER.get()));
                        output.accept(new ItemStack(ItemsRegistry.NETHER_PORTER.get()));
                        output.accept(new ItemStack(ItemsRegistry.NETHER_STAR_LAGER.get()));
                        output.accept(new ItemStack(ItemsRegistry.NIGHT_RAUCH.get()));
                        output.accept(new ItemStack(ItemsRegistry.SUN_PALE_ALE.get()));
                        output.accept(new ItemStack(ItemsRegistry.WITHER_STOUT.get()));
                    }))
                    .build()
    );

    public static void register() {
        TAB.register();
    }
}
