package com.grasshopper.darkbluemoon.entity;

import java.util.List;
import java.util.UUID;

import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.config.DarkBlueMoonConfig;
import com.grasshopper.darkbluemoon.init.InitEntities;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class DarkBlueMoonWhirlpoolEntity extends Entity {

    private static final DataParameter<Integer> WATER_COUNT = EntityDataManager.defineId(
            DarkBlueMoonWhirlpoolEntity.class, DataSerializers.INT);

    private UUID ownerUUID;
    private int tickCount = 0;

    public DarkBlueMoonWhirlpoolEntity(EntityType<? extends DarkBlueMoonWhirlpoolEntity> type, World world) {
        super(type, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public DarkBlueMoonWhirlpoolEntity(World world, LivingEntity owner, Vector3d center) {
        super(InitEntities.WHIRLPOOL.get(), world);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.ownerUUID = owner.getUUID();
        setPos(center.x, center.y, center.z);
        if (!world.isClientSide()) {
            setWaterCount(countWater(world, blockPosition()));
        }
    }

    public int getTickCount() {
        return tickCount;
    }

    public int getWaterCount() {
        return entityData.get(WATER_COUNT);
    }

    public void setWaterCount(int count) {
        entityData.set(WATER_COUNT, count);
    }

    public LivingEntity getOwner() {
        if (ownerUUID == null) return null;
        if (!(level instanceof ServerWorld)) return null;
        Entity e = ((ServerWorld) level).getEntity(ownerUUID);
        return e instanceof LivingEntity ? (LivingEntity) e : null;
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;

        int chargeTime = DarkBlueMoonConfig.WHIRLPOOL_CHARGE_TIME.get();
        int lifespan = DarkBlueMoonConfig.WHIRLPOOL_LIFESPAN.get();
        double radius = DarkBlueMoonConfig.WHIRLPOOL_RADIUS.get();
        double pullSpeed = DarkBlueMoonConfig.WHIRLPOOL_PULL_SPEED.get();
        double minDamage = DarkBlueMoonConfig.WHIRLPOOL_MIN_DAMAGE.get();
        double maxDamage = DarkBlueMoonConfig.WHIRLPOOL_MAX_DAMAGE.get();

        if (level.isClientSide()) {
            return;
        }

        if (tickCount >= lifespan) {
            remove();
            return;
        }

        if (tickCount % 20 == 0) {
            setWaterCount(countWater(level, blockPosition()));
        }

        if (tickCount >= chargeTime && tickCount % 20 == 0) {
            SoundEvent[] useSounds = new SoundEvent[] {
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_1.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_2.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_3.get()
            };
            SoundEvent chosen = useSounds[level.random.nextInt(useSounds.length)];
            playSound(chosen, 1.0F, 1.0F);
        }

        if (tickCount < chargeTime) {
            return;
        }

        LivingEntity owner = getOwner();
        if (owner == null) {
            remove();
            return;
        }

        List<Entity> entities = level.getEntitiesOfClass(Entity.class,
                getBoundingBox().inflate(radius),
                e -> e != owner && e.isAlive());

        for (Entity entity : entities) {
            if (isImmune(entity)) {
                continue;
            }

            Vector3d toCenter = position().subtract(entity.position());
            double dist = toCenter.length();
            if (dist < 0.5D) {
                continue;
            }

            Vector3d pull = toCenter.normalize().scale(pullSpeed);
            entity.setDeltaMovement(entity.getDeltaMovement().add(pull));
            entity.hurtMarked = true;

            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                double ratio = 1.0D - (dist / radius);
                float damage = (float) (minDamage + ratio * (maxDamage - minDamage));
                living.hurt(DamageSource.MAGIC, damage);
            }
        }
    }

    private int countWater(World world, BlockPos center) {
        int radius = DarkBlueMoonConfig.WHIRLPOOL_WATER_CHECK_RADIUS.get();
        int count = 0;
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius))) {
            if (world.getBlockState(pos).getMaterial() == Material.WATER) {
                count++;
            }
        }
        return count;
    }

    private boolean isImmune(Entity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            IStandPower power = IStandPower.getPlayerStandPower(player);
            if (power != null && power.hasPower()) {
                IStandManifestation stand = power.getStandManifestation();
                if (stand instanceof DarkBlueMoonEntity) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(WATER_COUNT, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("OwnerUUID")) {
            ownerUUID = nbt.getUUID("OwnerUUID");
        }
        tickCount = nbt.getInt("TickCount");
        entityData.set(WATER_COUNT, nbt.getInt("WaterCount"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (ownerUUID != null) {
            nbt.putUUID("OwnerUUID", ownerUUID);
        }
        nbt.putInt("TickCount", tickCount);
        nbt.putInt("WaterCount", getWaterCount());
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