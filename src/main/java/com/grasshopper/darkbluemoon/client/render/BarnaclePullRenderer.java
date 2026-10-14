package com.grasshopper.darkbluemoon.client.render;

import com.github.standobyte.jojo.client.render.entity.renderer.damaging.extending.ExtendingEntityRenderer;
import com.grasshopper.darkbluemoon.entity.BarnaclePullEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BarnaclePullRenderer extends ExtendingEntityRenderer<BarnaclePullEntity, BarnaclePullModel<BarnaclePullEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "darkbluemoon", "textures/entity/barnacle_pull.png");

    public BarnaclePullRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BarnaclePullModel<>(), TEXTURE);
    }
}