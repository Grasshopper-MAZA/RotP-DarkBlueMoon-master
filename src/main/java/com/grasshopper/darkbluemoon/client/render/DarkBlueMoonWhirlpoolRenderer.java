package com.grasshopper.darkbluemoon.client.render;

import com.grasshopper.darkbluemoon.entity.DarkBlueMoonWhirlpoolEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class DarkBlueMoonWhirlpoolRenderer extends EntityRenderer<DarkBlueMoonWhirlpoolEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "darkbluemoon", "textures/entity/whirlpool.png");

    private static final int MAX_WATER = 8000;
    private static final float MIN_SCALE = 1.0F;
    private static final float MAX_SCALE = 10.0F;

    private final DarkBlueMoonWhirlpoolModel model;

    public DarkBlueMoonWhirlpoolRenderer(EntityRendererManager manager) {
        super(manager);
        this.model = new DarkBlueMoonWhirlpoolModel();
    }

    @Override
    public void render(DarkBlueMoonWhirlpoolEntity entity, float entityYaw, float partialTicks,
                       MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();

        int water = entity.getWaterCount();
        float ratio = Math.min((float) water / MAX_WATER, 1.0F);
        float scale = MIN_SCALE + ratio * (MAX_SCALE - MIN_SCALE);
        this.model.setScale(scale);

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.entityCutout(TEXTURE));
        this.model.setupAnim(entity, 0, 0, entity.getTickCount() + partialTicks, 0, 0);
        this.model.renderToBuffer(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DarkBlueMoonWhirlpoolEntity entity) {
        return TEXTURE;
    }
}