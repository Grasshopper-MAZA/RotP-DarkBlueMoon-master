package com.grasshopper.darkbluemoon.entity;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.OwnerBoundProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.grasshopper.darkbluemoon.init.InitEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BarnaclePullEntity extends OwnerBoundProjectileEntity {

    private StandEntity ownerStand;
    private IStandPower userStandPower;
    private UUID userU;

    public BarnaclePullEntity(World world, LivingEntity user, StandEntity stand, IStandPower standPower) {
        super(InitEntities.BARNACLE_PULL.get(), user, world);
        this.ownerStand = stand;
        this.userStandPower = standPower;
        this.userU = user.getUUID();
        setLifeSpan(40);
    }

    public BarnaclePullEntity(EntityType<? extends BarnaclePullEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean standDamage() {
        return true;
    }

    @Override
    public float getBaseDamage() {
        return 2.0F;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0.0F;
    }

    @Override
    protected float movementSpeed() {
        return 1.5F;
    }

    @Override
    public int ticksLifespan() {
        return getEntityAttachedTo() == null ? 40 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isBodyPart() {
        return true;
    }

    private static final Vector3d OFFSET = new Vector3d(0, -0.2, 0);

    @Override
    protected Vector3d getOwnerRelativeOffset() {
        return OFFSET;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isAlive()) {
            return;
        }

        LivingEntity bound = getEntityAttachedTo();
        if (bound != null) {
            LivingEntity owner = getOwner();
            if (owner == null) {
                remove();
                return;
            }

            if (!bound.isAlive() || owner.distanceToSqr(bound) > 100) {
                if (!level.isClientSide()) {
                    remove();
                }
                return;
            }

            Vector3d vecToOwner = owner.position().subtract(bound.position());
            double length = vecToOwner.length();

            if (length < 2.0D) {
                if (!level.isClientSide()) {
                    remove();
                }
                return;
            }

            dragTarget(bound, vecToOwner.normalize().scale(0.8D));
            bound.fallDistance = 0;
            bound.addEffect(new EffectInstance(
                    Effects.MOVEMENT_SLOWDOWN, 20, 4,
                    false, false, false
            ));
        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        LivingEntity owner = getOwner();
        if (entity.is(owner) || !(entity instanceof LivingEntity)) {
            return false;
        }
        if (owner instanceof StandEntity) {
            StandEntity stand = (StandEntity) owner;
            return !entity.is(stand.getUser()) || !stand.isFollowingUser();
        }
        return true;
    }

    @Override
    protected boolean hurtTarget(Entity target, LivingEntity owner) {
        if (getEntityAttachedTo() == null && target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            if (!JojoModUtil.isTargetBlocking(livingTarget)) {
                attachToEntity(livingTarget);
                target.addTag(String.valueOf(userU));
                return false;
            }
        }
        return false;
    }

    @Override
    protected void updateMotionFlags() {}

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.userU != null) {
            nbt.putUUID("UserServerId", this.userU);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.hasUUID("UserServerId")) {
            this.userU = nbt.getUUID("UserServerId");
        }
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        super.writeSpawnData(buffer);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
    }

    @Nullable
    public StandEntity getOwnerAsStand() {
        if (ownerStand == null) {
            LivingEntity owner = getOwner();
            ownerStand = owner instanceof StandEntity ? (StandEntity) owner : null;
        }
        return ownerStand;
    }
}