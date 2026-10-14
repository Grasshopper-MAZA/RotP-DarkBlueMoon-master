package com.grasshopper.darkbluemoon.client.render;

import com.grasshopper.darkbluemoon.entity.ScaleShotEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ScaleShotRenderer extends EntityRenderer<ScaleShotEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "darkbluemoon", "textures/entity/scale_shot.png");

    private final ModelRenderer scale;

    public ScaleShotRenderer(EntityRendererManager manager) {
        super(manager);
        this.scale = new ModelRenderer(16, 16, 0, 0);
        this.scale.addBox(-1.0F, -1.5F, -2.5F, 2, 3, 5);
        this.scale.setPos(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(ScaleShotEntity entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();

        float yaw = MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot);
        float pitch = MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot);

        matrixStack.mulPose(Vector3f.YP.rotationDegrees(-yaw));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(pitch));

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.scale.render(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ScaleShotEntity entity) {
        return TEXTURE;
    }
}