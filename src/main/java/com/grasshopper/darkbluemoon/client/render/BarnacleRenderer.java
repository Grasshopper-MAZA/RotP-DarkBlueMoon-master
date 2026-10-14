package com.grasshopper.darkbluemoon.client.render;

import com.grasshopper.darkbluemoon.entity.BarnacleEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class BarnacleRenderer extends EntityRenderer<BarnacleEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "darkbluemoon", "textures/entity/barnacle.png");

    private final ModelRenderer barnacle;

    public BarnacleRenderer(EntityRendererManager manager) {
        super(manager);
        this.barnacle = new ModelRenderer(8, 8, 0, 0);
        this.barnacle.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2);
        this.barnacle.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(BarnacleEntity entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.barnacle.render(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BarnacleEntity entity) {
        return TEXTURE;
    }
}