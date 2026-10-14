package com.grasshopper.darkbluemoon.entity;

import java.util.List;
import java.util.UUID;

import com.grasshopper.darkbluemoon.init.InitEntities;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class DarkBlueMoonMiniTornadoEntity extends Entity {

    private static final double RADIUS = 4.0D;
    private static final int LIFESPAN = 100;
    private static final float DAMAGE = 2.0F;

    private UUID ownerUUID;
    private int tickCount = 0;

    public DarkBlueMoonMiniTornadoEntity(EntityType<? extends DarkBlueMoonMiniTornadoEntity> type, World world) {
        super(type, world);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public DarkBlueMoonMiniTornadoEntity(World world, LivingEntity owner, Vector3d center) {
        super(InitEntities.MINI_WATER_TORNADO.get(), world);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.ownerUUID = owner.getUUID();
        setPos(center.x, center.y, center.z);
    }

    public int getTickCount() {
        return tickCount;
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

        if (level.isClientSide()) {
            return;
        }

        if (tickCount >= LIFESPAN) {
            remove();
            return;
        }

        LivingEntity owner = getOwner();
        if (owner == null) {
            remove();
            return;
        }

        Vector3d motion = getDeltaMovement();
        if (motion.lengthSqr() > 0.001D) {
            setPos(getX() + motion.x, getY() + motion.y, getZ() + motion.z);
        }

        if (tickCount == 1) {
            SoundEvent[] useSounds = new SoundEvent[] {
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_1.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_2.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_3.get()
            };
            SoundEvent chosen = useSounds[level.random.nextInt(useSounds.length)];
            playSound(chosen, 1.0F, 1.0F);
        }

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(RADIUS),
                e -> e != owner && e.isAlive());

        for (LivingEntity target : entities) {
            Vector3d toCenter = position().subtract(target.position());
            double dist = toCenter.length();
            if (dist < 0.5D) continue;

            Vector3d pull = toCenter.normalize().scale(0.35D);
            target.setDeltaMovement(target.getDeltaMovement().add(pull));
            target.hurtMarked = true;

            target.hurt(DamageSource.MAGIC, DAMAGE);
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 40, 1, false, false, false));
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        if (nbt.hasUUID("OwnerUUID")) {
            ownerUUID = nbt.getUUID("OwnerUUID");
        }
        tickCount = nbt.getInt("TickCount");
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        if (ownerUUID != null) {
            nbt.putUUID("OwnerUUID", ownerUUID);
        }
        nbt.putInt("TickCount", tickCount);
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