package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonMiniTornadoEntity;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DarkBlueMoonMiniTornadoAction extends StandEntityHeavyAttack {

    public static final StandPose MINI_TORNADO_POSE = new StandPose("finisherPunch");

    private static final float LAND_BONUS_DAMAGE = 4.0F;

    public DarkBlueMoonMiniTornadoAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.NONE;
    }

    @Override
    public StandRelativeOffset getOffsetFromUser(IStandPower standPower, StandEntity standEntity, StandEntityTask task) {
        return StandRelativeOffset.withYOffset(0, 0, 1.2);
    }

    @Override
    public ActionConditionResult checkConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        ActionConditionResult base = super.checkConditions(user, power, target);
        if (!base.isPositive()) {
            return base;
        }
        if (user == null) {
            return ActionConditionResult.NEGATIVE;
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
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

        if (!inWater) {
            Vector3d look = user.getLookAngle();
            Vector3d start = standEntity.position().add(0.0D, standEntity.getBbHeight() * 0.5D, 0.0D);

            for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class,
                    standEntity.getBoundingBox().inflate(3.0D),
                    e -> e != user && e != standEntity && e.isAlive())) {

                Vector3d toTarget = target.position().subtract(start);
                double dist = toTarget.length();
                if (dist > 3.0D) continue;
                double dot = toTarget.normalize().dot(look);
                if (dot < 0.5D) continue;

                target.hurt(net.minecraft.util.DamageSource.mobAttack(user), LAND_BONUS_DAMAGE);
                break;
            }
            return;
        }

        Vector3d look = user.getLookAngle();
        Vector3d center = standEntity.position().add(look.scale(1.5D));

        DarkBlueMoonMiniTornadoEntity tornado = new DarkBlueMoonMiniTornadoEntity(world, user, center);
        tornado.setDeltaMovement(look.scale(0.6D));
        world.addFreshEntity(tornado);
    }
}