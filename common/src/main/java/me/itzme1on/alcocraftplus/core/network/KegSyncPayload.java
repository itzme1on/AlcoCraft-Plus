package me.itzme1on.alcocraftplus.core.network;

import me.itzme1on.alcocraftplus.core.recipes.KegRecipes;
import me.itzme1on.alcocraftplus.core.utils.IdentifierUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public record KegSyncPayload(List<KegRecipes> recipes) implements CustomPacketPayload {
    public static final Type<KegSyncPayload> TYPE = new Type<>(IdentifierUtil.of("keg_sync"));

    private static final StreamCodec<RegistryFriendlyByteBuf, List<KegRecipes>> LIST_CODEC =
            ByteBufCodecs.collection(ArrayList::new, KegRecipes.STREAM_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, KegSyncPayload> STREAM_CODEC =
            LIST_CODEC.map(KegSyncPayload::new, KegSyncPayload::recipes);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
