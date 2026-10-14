package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.config.DarkBlueMoonConfig;
import com.grasshopper.darkbluemoon.event.BarnacleBindHandler;
import com.grasshopper.darkbluemoon.init.InitEffects;
import com.grasshopper.darkbluemoon.util.BarnacleItemHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DarkBlueMoonBarnacleAction extends StandEntityAction {

    public static final StandPose BARNACLE_POSE = new StandPose("whirlpool");

    public DarkBlueMoonBarnacleAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
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
        if (user == null || !BarnacleItemHelper.canUseBarnacleSkill(user)) {
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

        if (!user.isInWater() && !user.isUnderWater()) {
            int cost = BarnacleItemHelper.getRandomCost(user);
            if (!BarnacleItemHelper.consumeBarnacle(user, cost)) {
                return;
            }
        }

        Vector3d look = user.getLookAngle();
        Vector3d start = standEntity.position().add(0.0D, standEntity.getBbHeight() * 0.5D, 0.0D);

        int duration = DarkBlueMoonConfig.BARNACLE_BIND_DURATION.get();
        double radius = DarkBlueMoonConfig.BARNACLE_BIND_RADIUS.get();

        for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class,
                standEntity.getBoundingBox().inflate(radius),
                e -> e != user && e != standEntity && e.isAlive())) {

            Vector3d toTarget = target.position().subtract(start);
            double dist = toTarget.length();
            if (dist > radius) {
                continue;
            }
            double dot = toTarget.normalize().dot(look);
            if (dot < 0.5D) {
                continue;
            }

            target.addEffect(new EffectInstance(
                    InitEffects.BARNACLE_BIND.get(),
                    duration, 0,
                    false, false, false
            ));

            BarnacleBindHandler.bind(user, target);
            break;
        }
    }
}