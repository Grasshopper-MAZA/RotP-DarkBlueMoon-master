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
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;
import com.grasshopper.darkbluemoon.util.BarnacleItemHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DarkBlueMoonBarnacleTrapAction extends StandEntityAction {

    public static final StandPose BARNACLE_TRAP_POSE = new StandPose("whirlpool");

    public DarkBlueMoonBarnacleTrapAction(Builder builder) {
        super(builder);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.BLOCK;
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

        if (!(standEntity instanceof DarkBlueMoonEntity)) {
            return;
        }

        LivingEntity user = standEntity.getUser();
        if (user == null) {
            return;
        }

        DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) standEntity;

        if (dbm.hasBarnacleTrap()) {
            dbm.clearBarnacleTrap();
            return;
        }

        if (!user.isInWater() && !user.isUnderWater()) {
            int cost = BarnacleItemHelper.getRandomCost(user);
            if (!BarnacleItemHelper.consumeBarnacle(user, cost)) {
                return;
            }
        }

        ActionTarget target = task.getTarget();
        if (target.getType() != ActionTarget.TargetType.BLOCK) {
            return;
        }

        BlockPos blockPos = target.getBlockPos();
        if (blockPos == null) {
            return;
        }

        boolean userInWater = user.isInWater() || user.isUnderWater();
        boolean trapInWater = userInWater || isBlockInWater(world, blockPos);

        if (!trapInWater) {
            if (!hasWaterNearby(world, blockPos, 5)) {
                return;
            }
        }

        dbm.setBarnacleTrapPos(blockPos);
    }

    private boolean isBlockInWater(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getMaterial() == Material.WATER;
    }

    private boolean hasWaterNearby(World world, BlockPos center, int radius) {
        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius))) {
            if (world.getBlockState(pos).getMaterial() == Material.WATER) {
                return true;
            }
        }
        return false;
    }
}