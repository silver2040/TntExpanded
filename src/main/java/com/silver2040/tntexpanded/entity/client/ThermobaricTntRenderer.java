package com.silver2040.tntexpanded.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.silver2040.tntexpanded.TntExpanded;
import com.silver2040.tntexpanded.entity.blocks.ThermobaricTntEntity;
import com.silver2040.tntexpanded.registry.TntBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ThermobaricTntRenderer extends EntityRenderer<ThermobaricTntEntity> {

    private final BlockRenderDispatcher blockRenderer;

    public ThermobaricTntRenderer(EntityRendererProvider.Context p_174426_) {
        super(p_174426_);
        this.shadowRadius = 0.5F;
        this.blockRenderer = p_174426_.getBlockRenderDispatcher();
    }
    @Override
    public void render(ThermobaricTntEntity blockEntity, float partialTicks, float combinedOverlay, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);
        int $$6 = blockEntity.getFuse();
        if ((float)$$6 - combinedOverlay + 1.0F < 10.0F) {
            float $$7 = 1.0F - ((float)$$6 - combinedOverlay + 1.0F) / 10.0F;
            $$7 = Mth.clamp($$7, 0.0F, 1.0F);
            $$7 *= $$7;
            $$7 *= $$7;
            float $$8 = 1.0F + $$7 * 0.3F;
            poseStack.scale($$8, $$8, $$8);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, TntBlocks.THERMOBARIC_TNT.get().defaultBlockState(), poseStack, buffer, packedLight, $$6 / 5 % 2 == 0);
        poseStack.popPose();
        super.render(blockEntity, partialTicks, combinedOverlay, poseStack, buffer, packedLight);

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThermobaricTntEntity tntEntity) {
        return new ResourceLocation(TntExpanded.MODID, "textures/entity/thermobaric_side.png");
    }
}