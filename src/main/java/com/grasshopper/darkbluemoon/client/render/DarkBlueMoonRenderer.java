package com.grasshopper.darkbluemoon.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DarkBlueMoonRenderer extends StandEntityRenderer<DarkBlueMoonEntity, StandEntityModel<DarkBlueMoonEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            DarkBlueMoonMain.MOD_ID, "textures/entity/stand/dark_blue_moon.png");

    public DarkBlueMoonRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(
                        new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon"),
                        DarkBlueMoonModel::new),
                TEXTURE, 0);
    }
}