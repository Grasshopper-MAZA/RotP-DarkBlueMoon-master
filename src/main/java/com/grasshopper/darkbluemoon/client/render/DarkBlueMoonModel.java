package com.grasshopper.darkbluemoon.client.render;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.github.standobyte.jojo.client.render.entity.bb.EntityModelUnbaked;
import com.github.standobyte.jojo.client.render.entity.bb.ParseGeckoModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.bb.BlockbenchStandModelHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class DarkBlueMoonModel extends HumanoidStandModel<DarkBlueMoonEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(
            DarkBlueMoonMain.MOD_ID, "geo/dark_blue_moon.geo.json");

    public ModelRenderer fin;
    public ModelRenderer fin2;
    public ModelRenderer fin3;
    public ModelRenderer fin4;
    public ModelRenderer fin5;
    public ModelRenderer fin6;
    public ModelRenderer fin7;
    public ModelRenderer fin8;
    public ModelRenderer fin9;
    public ModelRenderer fin10;
    public ModelRenderer fin11;
    public ModelRenderer fin12;
    public ModelRenderer fin13;
    public ModelRenderer fin14;
    public ModelRenderer fin15;
    public ModelRenderer fin16;
    public ModelRenderer fin17;
    public ModelRenderer fin18;

    public DarkBlueMoonModel() {
        super(128, 128);
        loadBlockbenchModel();
    }

    private void loadBlockbenchModel() {
        try (IResource resource = Minecraft.getInstance().getResourceManager().getResource(MODEL);
             Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            JsonElement json = new JsonParser().parse(reader);
            EntityModelUnbaked unbaked = ParseGeckoModel.parseGeckoModel(json, MODEL);
            BlockbenchStandModelHelper.fillFromUnbaked(unbaked, this);
        } catch (Exception e) {
            DarkBlueMoonMain.LOGGER.error("Failed to load Dark Blue Moon model", e);
        }
    }

    @Override
    public ModelRenderer putNamedModelPart(String name, ModelRenderer modelPart) {
        ModelRenderer result = super.putNamedModelPart(name, modelPart);
        if (name == null || modelPart == null) return result;
        switch (name) {
            case "fin":   this.fin = modelPart; break;
            case "fin2":  this.fin2 = modelPart; break;
            case "fin3":  this.fin3 = modelPart; break;
            case "fin4":  this.fin4 = modelPart; break;
            case "fin5":  this.fin5 = modelPart; break;
            case "fin6":  this.fin6 = modelPart; break;
            case "fin7":  this.fin7 = modelPart; break;
            case "fin8":  this.fin8 = modelPart; break;
            case "fin9":  this.fin9 = modelPart; break;
            case "fin10": this.fin10 = modelPart; break;
            case "fin11": this.fin11 = modelPart; break;
            case "fin12": this.fin12 = modelPart; break;
            case "fin13": this.fin13 = modelPart; break;
            case "fin14": this.fin14 = modelPart; break;
            case "fin15": this.fin15 = modelPart; break;
            case "fin16": this.fin16 = modelPart; break;
            case "fin17": this.fin17 = modelPart; break;
            case "fin18": this.fin18 = modelPart; break;
            case "body":      this.body = modelPart; break;
            case "upperPart": this.upperPart = modelPart; break;
            case "torso":     this.torso = modelPart; break;
            default: break;
        }
        return result;
    }

    @Override
    public void setupAnim(DarkBlueMoonEntity entity, float walkAnimPos, float walkAnimSpeed,
                          float ticks, float yRotationOffset, float xRotation) {
        super.setupAnim(entity, walkAnimPos, walkAnimSpeed, ticks, yRotationOffset, xRotation);

        if (this.head != null) {
            this.head.xRot = (float) Math.toRadians(xRotation);
            this.head.yRot = (float) Math.toRadians(yRotationOffset);
            this.head.zRot = 0;
        }
    }
}