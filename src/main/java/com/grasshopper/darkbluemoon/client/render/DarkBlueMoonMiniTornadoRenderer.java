package com.grasshopper.darkbluemoon.client.render;

import com.grasshopper.darkbluemoon.entity.DarkBlueMoonMiniTornadoEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class DarkBlueMoonMiniTornadoRenderer extends EntityRenderer<DarkBlueMoonMiniTornadoEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "darkbluemoon", "textures/entity/deep_sea_water_tornado.png");

    private final ModelRenderer tornado;

    public DarkBlueMoonMiniTornadoRenderer(EntityRendererManager manager) {
        super(manager);
        this.tornado = new ModelRenderer(64, 64, 0, 0);
        this.tornado.addBox(-4.0F, -8.0F, -4.0F, 8, 16, 8, 0.0F);
        this.tornado.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(DarkBlueMoonMiniTornadoEntity entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.tornado.yRot = (float) Math.toRadians((-entity.getTickCount() * 360.0D / 10.0D) % 360.0D);
        this.tornado.xRot = (float) Math.toRadians(180.0D);
        this.tornado.render(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DarkBlueMoonMiniTornadoEntity entity) {
        return TEXTURE;
    }
}