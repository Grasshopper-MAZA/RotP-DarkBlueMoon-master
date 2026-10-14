package com.grasshopper.darkbluemoon.entity;

import java.util.UUID;

import com.grasshopper.darkbluemoon.init.InitEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class BarnacleEntity extends Entity {

    private static final DataParameter<Integer> TARGET_ID = EntityDataManager.defineId(
            BarnacleEntity.class, DataSerializers.INT);
    private static final DataParameter<Float> OFFSET_X = EntityDataManager.defineId(
            BarnacleEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> OFFSET_Y = EntityDataManager.defineId(
            BarnacleEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> OFFSET_Z = EntityDataManager.defineId(
            BarnacleEntity.class, DataSerializers.FLOAT);

    private UUID targetUUID;

    public BarnacleEntity(EntityType<? extends BarnacleEntity> type, World world) {
        super(type, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public BarnacleEntity(World world, LivingEntity target, Vector3d localOffset) {
        super(InitEntities.BARNACLE.get(), world);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.targetUUID = target.getUUID();
        setTargetId(target.getId());
        setOffset(localOffset);
        updatePositionFromTarget(target);
    }

    public void setTargetId(int id) {
        entityData.set(TARGET_ID, id);
    }

    public int getTargetId() {
        return entityData.get(TARGET_ID);
    }

    public void setOffset(Vector3d offset) {
        entityData.set(OFFSET_X, (float) offset.x);
        entityData.set(OFFSET_Y, (float) offset.y);
        entityData.set(OFFSET_Z, (float) offset.z);
    }

    public Vector3d getOffset() {
        return new Vector3d(
                entityData.get(OFFSET_X),
                entityData.get(OFFSET_Y),
                entityData.get(OFFSET_Z)
        );
    }

    public LivingEntity getTarget() {
        if (!(level instanceof ServerWorld)) {
            Entity e = level.getEntity(getTargetId());
            return e instanceof LivingEntity ? (LivingEntity) e : null;
        }
        if (targetUUID == null) return null;
        Entity e = ((ServerWorld) level).getEntity(targetUUID);
        return e instanceof LivingEntity ? (LivingEntity) e : null;
    }

    private void updatePositionFromTarget(LivingEntity target) {
        Vector3d offset = getOffset();
        setPos(
                target.getX() + offset.x,
                target.getY() + offset.y,
                target.getZ() + offset.z
        );
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = getTarget();

        if (target == null || !target.isAlive()) {
            if (!level.isClientSide()) {
                remove();
            }
            return;
        }

        updatePositionFromTarget(target);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_ID, -1);
        entityData.define(OFFSET_X, 0F);
        entityData.define(OFFSET_Y, 0F);
        entityData.define(OFFSET_Z, 0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("TargetUUID")) {
            targetUUID = nbt.getUUID("TargetUUID");
        }
        entityData.set(OFFSET_X, nbt.getFloat("OffX"));
        entityData.set(OFFSET_Y, nbt.getFloat("OffY"));
        entityData.set(OFFSET_Z, nbt.getFloat("OffZ"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (targetUUID != null) {
            nbt.putUUID("TargetUUID", targetUUID);
        }
        nbt.putFloat("OffX", (float) getOffset().x);
        nbt.putFloat("OffY", (float) getOffset().y);
        nbt.putFloat("OffZ", (float) getOffset().z);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}