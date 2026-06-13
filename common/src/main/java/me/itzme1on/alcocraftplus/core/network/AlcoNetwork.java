package me.itzme1on.alcocraftplus.core.network;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.ReloadListenerRegistry;
import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public final class AlcoNetwork {
    private static volatile MinecraftServer currentServer;

    private AlcoNetwork() {
    }

    public static void init() {
        NetworkManager.registerReceiver(
                NetworkManager.s2c(),
                KegSyncPayload.TYPE,
                KegSyncPayload.STREAM_CODEC,
                (payload, context) -> context.queue(() -> ClientKegData.set(payload.recipes()))
        );

        PlayerEvent.PLAYER_JOIN.register(player -> {
            MinecraftServer server = player.level().getServer();
            NetworkManager.sendToPlayer(player, payloadFor(server));
        });

        LifecycleEvent.SERVER_STARTED.register(server -> currentServer = server);
        LifecycleEvent.SERVER_STOPPED.register(server -> currentServer = null);
        ReloadListenerRegistry.register(
                PackType.SERVER_DATA,
                (ResourceManagerReloadListener) resourceManager -> {
                    MinecraftServer server = currentServer;
                    if (server != null) server.execute(() -> resyncAll(server));
                },
                IdentifierUtil.of("keg_sync")
        );
    }

    private static void resyncAll(MinecraftServer server) {
        NetworkManager.sendToPlayers(server.getPlayerList().getPlayers(), payloadFor(server));
    }

    private static KegSyncPayload payloadFor(MinecraftServer server) {
        return new KegSyncPayload(collectKegRecipes(server));
    }

    public static List<KegRecipes> collectKegRecipes(MinecraftServer server) {
        List<KegRecipes> recipes = new ArrayList<>();

        for (RecipeHolder<?> holder : server.getRecipeManager().getRecipes()) {
            if (holder.value() instanceof KegRecipes keg) {
                recipes.add(keg);
            }
        }

        return recipes;
    }
}
