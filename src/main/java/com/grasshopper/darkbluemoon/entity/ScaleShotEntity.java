package com.grasshopper.darkbluemoon.entity;

import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.grasshopper.darkbluemoon.init.InitEntities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ScaleShotEntity extends ModdedProjectileEntity {

    private boolean inWaterMode = false;

    public ScaleShotEntity(LivingEntity shooter, World world, boolean inWaterMode) {
        super(InitEntities.SCALE_SHOT.get(), shooter, world);
        this.inWaterMode = inWaterMode;
    }

    public ScaleShotEntity(EntityType<? extends ScaleShotEntity> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            LivingEntity owner = getOwner();
            if (owner == null || owner instanceof StandEntity) {
                remove();
                return;
            }
        }
        super.tick();
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    public float getBaseDamage() {
        return inWaterMode ? 5.0F : 2.0F;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return inWaterMode ? 1.5F : 0.5F;
    }

    @Override
    public int ticksLifespan() {
        return inWaterMode ? 100 : 20;
    }

    @Override
    protected boolean hasGravity() {
        return false;
    }

    @Override
    protected boolean constVelocity() {
        return true;
    }

    @Override
    protected Vector3d getOwnerRelativeOffset() {
        return new Vector3d(0.0, -0.3, 0.75);
    }

    @Override
    protected DamageSource getDamageSource(LivingEntity owner) {
        if (owner instanceof StandEntity) {
            LivingEntity user = ((StandEntity) owner).getUser();
            owner = (user != null) ? user : null;
        }
        return super.getDamageSource(owner);
    }
}