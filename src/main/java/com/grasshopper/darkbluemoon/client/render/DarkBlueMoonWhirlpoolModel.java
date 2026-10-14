package com.grasshopper.darkbluemoon.client.render;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.github.standobyte.jojo.client.render.entity.bb.EntityModelUnbaked;
import com.github.standobyte.jojo.client.render.entity.bb.ParseGeckoModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.bb.BlockbenchStandModelHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonWhirlpoolEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class DarkBlueMoonWhirlpoolModel extends EntityModel<DarkBlueMoonWhirlpoolEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(
            DarkBlueMoonMain.MOD_ID, "geo/whirlpool.geo.json");

    private final ModelRenderer root;

    public DarkBlueMoonWhirlpoolModel() {
        super();
        this.root = new ModelRenderer(this, 0, 0);
        loadBlockbenchModel();
    }

    private void loadBlockbenchModel() {
        try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(MODEL);
             Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            JsonElement json = new JsonParser().parse(reader);
            EntityModelUnbaked unbaked = ParseGeckoModel.parseGeckoModel(json, MODEL);
            BlockbenchStandModelHelper.fillFromUnbaked(unbaked, this);
        } catch (Exception e) {
            DarkBlueMoonMain.LOGGER.error("Failed to load Whirlpool Blockbench model", e);
        }
    }

    @Override
    public void setupAnim(DarkBlueMoonWhirlpoolEntity entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (this.root != null) {
            this.root.yRot = (float) Math.toRadians((entity.getTickCount() * 360.0D / 35.0D) % 360.0D);
            this.root.xRot = (float) Math.toRadians(180.0D);
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        matrixStack.scale(scale, scale, scale);
        this.root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.popPose();
    }

    private float scale = 3.0F;

    public void setScale(float scale) {
        this.scale = scale;
    }
}