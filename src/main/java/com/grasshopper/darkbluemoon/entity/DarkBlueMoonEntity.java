package com.grasshopper.darkbluemoon.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.grasshopper.darkbluemoon.init.InitEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DarkBlueMoonEntity extends StandEntity {

    private static final DataParameter<Boolean> HAS_PICKAXE = EntityDataManager.defineId(
            DarkBlueMoonEntity.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Boolean> HAS_BARNACLE_TRAP = EntityDataManager.defineId(
            DarkBlueMoonEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> TRAP_X = EntityDataManager.defineId(
            DarkBlueMoonEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> TRAP_Y = EntityDataManager.defineId(
            DarkBlueMoonEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> TRAP_Z = EntityDataManager.defineId(
            DarkBlueMoonEntity.class, DataSerializers.FLOAT);

    public DarkBlueMoonEntity(StandEntityType<DarkBlueMoonEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_PICKAXE, false);
        this.entityData.define(HAS_BARNACLE_TRAP, false);
        this.entityData.define(TRAP_X, 0F);
        this.entityData.define(TRAP_Y, 0F);
        this.entityData.define(TRAP_Z, 0F);
    }

    public boolean hasPickaxe() {
        return this.entityData.get(HAS_PICKAXE);
    }

    public void setHasPickaxe(boolean hasPickaxe) {
        this.entityData.set(HAS_PICKAXE, hasPickaxe);
    }

    public boolean hasBarnacleTrap() {
        return this.entityData.get(HAS_BARNACLE_TRAP);
    }

    public void setBarnacleTrapPos(BlockPos pos) {
        this.entityData.set(TRAP_X, (float) pos.getX());
        this.entityData.set(TRAP_Y, (float) pos.getY());
        this.entityData.set(TRAP_Z, (float) pos.getZ());
        this.entityData.set(HAS_BARNACLE_TRAP, true);
    }

    public void clearBarnacleTrap() {
        this.entityData.set(HAS_BARNACLE_TRAP, false);
    }

    public BlockPos getBarnacleTrapPos() {
        return new BlockPos(
                (int) this.entityData.get(TRAP_X).floatValue(),
                (int) this.entityData.get(TRAP_Y).floatValue(),
                (int) this.entityData.get(TRAP_Z).floatValue()
        );
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            return;
        }
        LivingEntity user = getUser();
        boolean inWater = user != null && user.isAlive()
                && (user.isInWater() || user.isUnderWater());
        if (inWater) {
            this.addEffect(new EffectInstance(
                    InitEffects.DARK_BLUE_MOON_WATER_STAND.get(),
                    40, 0, false, false, true));
        } else {
            this.removeEffect(InitEffects.DARK_BLUE_MOON_WATER_STAND.get());
        }
    }
}