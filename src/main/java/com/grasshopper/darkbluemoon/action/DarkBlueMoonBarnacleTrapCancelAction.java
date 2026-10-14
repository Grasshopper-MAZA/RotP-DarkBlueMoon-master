package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class DarkBlueMoonBarnacleTrapCancelAction extends StandEntityAction {

    public static final StandPose BARNACLE_TRAP_CANCEL_POSE = new StandPose("whirlpool");

    public DarkBlueMoonBarnacleTrapCancelAction(Builder builder) {
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
        if (!(power.getStandManifestation() instanceof DarkBlueMoonEntity)) {
            return ActionConditionResult.NEGATIVE;
        }
        DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) power.getStandManifestation();
        if (!dbm.hasBarnacleTrap()) {
            return ActionConditionResult.NEGATIVE;
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            return;
        }

        if (!(standEntity instanceof DarkBlueMoonEntity)) {
            return;
        }

        DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) standEntity;
        dbm.clearBarnacleTrap();
    }
}