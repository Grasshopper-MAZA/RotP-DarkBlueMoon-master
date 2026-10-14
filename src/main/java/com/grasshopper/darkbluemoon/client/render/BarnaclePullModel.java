package com.grasshopper.darkbluemoon.client.render;

import com.github.standobyte.jojo.client.render.entity.model.ownerbound.repeating.RepeatingModel;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.OwnerBoundProjectileEntity;

import net.minecraft.client.renderer.model.ModelRenderer;

public class BarnaclePullModel<T extends OwnerBoundProjectileEntity> extends RepeatingModel<T> {

    private final ModelRenderer barnacle;

    public BarnaclePullModel() {
        texWidth = 16;
        texHeight = 16;

        barnacle = new ModelRenderer(this);
        barnacle.setPos(0.0F, 0.0F, 0.0F);
        barnacle.texOffs(0, 0).addBox(-1.0F, -1.0F, -2.0F, 2, 2, 4, 0.0F, false);
    }

    @Override
    protected ModelRenderer getMainPart() {
        return null;
    }

    @Override
    protected float getMainPartLength() {
        return 0;
    }

    @Override
    protected ModelRenderer getRepeatingPart() {
        return barnacle;
    }

    @Override
    protected float getRepeatingPartLength() {
        return 4.0F;
    }
}