package me.itzme1on.alcocraftplus.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.itzme1on.alcocraftplus.core.blocks.keg.KegEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class KegBlockEntityRenderer implements BlockEntityRenderer<KegEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public KegBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
    }

    @Override
    public void render(KegEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        BlockState blockState = blockEntity.getBlockState();

        BakedModel model = blockRenderer.getBlockModel(blockState);

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());

        blockRenderer.getModelRenderer().renderModel(
                poseStack.last(),
                vertexConsumer,
                blockState,
                model,
                1.0F, 1.0F, 1.0F,
                packedLight,
                packedOverlay
        );

        poseStack.popPose();
        buffer.endBatch(RenderType.solid());
    }
}
