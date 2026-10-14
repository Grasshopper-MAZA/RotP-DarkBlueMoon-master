package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonMiniTornadoEntity;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DarkBlueMoonBarrage extends StandEntityMeleeBarrage {

    private static final int CHARGE_TIME = 40;
    private boolean fired = false;

    public DarkBlueMoonBarrage(Builder builder) {
        super(builder.heldWalkSpeed(1).standUserWalkSpeed(1));
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        super.standPerform(world, standEntity, userPower, task);
        fired = false;

        if (world.isClientSide()) {
            return;
        }

        LivingEntity user = standEntity.getUser();
        if (user == null) {
            return;
        }

        boolean inWater = user.isInWater() || user.isUnderWater();
        if (inWater) {
            SoundEvent[] useSounds = new SoundEvent[] {
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_1.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_2.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_3.get()
            };
            SoundEvent chosen = useSounds[world.random.nextInt(useSounds.length)];
            standEntity.playSound(chosen, 1.0F, 1.0F);
        }
    }

    @Override
    public void rotateStand(StandEntity stand, StandEntityTask task) {
        super.rotateStand(stand, task);
        if (task.getPhase() == StandEntityAction.Phase.PERFORM) {
            aimBarrage(stand);
        }
    }

    @Override
    public void rotateStandTowardsTarget(StandEntity stand, ActionTarget target, StandEntityTask task) {
        super.rotateStandTowardsTarget(stand, target, task);
        if (task.getPhase() == StandEntityAction.Phase.PERFORM) {
            aimBarrage(stand);
        }
    }

    private void aimBarrage(StandEntity stand) {
        LivingEntity user = stand.getUser();
        if (user != null) {
            stand.yRot = user.yRot;
            stand.yRotO = user.yRotO;
            stand.xRot = user.xRot;
            stand.xRotO = user.xRotO;
            stand.yHeadRot = user.yHeadRot;
            stand.yHeadRotO = user.yHeadRotO;
            stand.yBodyRot = user.yBodyRot;
            stand.yBodyRotO = user.yBodyRotO;
        }
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        super.standTickPerform(world, standEntity, userPower, task);

        if (world.isClientSide() || fired) {
            return;
        }

        if (task.getTick() < CHARGE_TIME) {
            return;
        }

        LivingEntity user = standEntity.getUser();
        if (user == null) {
            return;
        }

        boolean inWater = user.isInWater() || user.isUnderWater();
        if (!inWater) {
            return;
        }

        fired = true;

        Vector3d look = user.getLookAngle();
        Vector3d center = standEntity.position().add(look.scale(1.5D));

        DarkBlueMoonMiniTornadoEntity tornado = new DarkBlueMoonMiniTornadoEntity(world, user, center);
        tornado.setDeltaMovement(look.scale(0.6D));
        world.addFreshEntity(tornado);
    }
}