package me.itzme1on.alcocraftplus.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class KegBlockEntityRenderer implements BlockEntityRenderer<KegEntity> {
    public KegBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(KegEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
    }
}
