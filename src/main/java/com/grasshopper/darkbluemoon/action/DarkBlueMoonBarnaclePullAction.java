package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.entity.BarnaclePullEntity;
import com.grasshopper.darkbluemoon.util.BarnacleItemHelper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class DarkBlueMoonBarnaclePullAction extends StandEntityAction {

    public static final StandPose BARNACLE_PULL_POSE = new StandPose("whirlpool");

    public DarkBlueMoonBarnaclePullAction(Builder builder) {
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

        BarnaclePullEntity pull = new BarnaclePullEntity(world, standEntity, standEntity, userPower);
        standEntity.addProjectile(pull);
    }
}