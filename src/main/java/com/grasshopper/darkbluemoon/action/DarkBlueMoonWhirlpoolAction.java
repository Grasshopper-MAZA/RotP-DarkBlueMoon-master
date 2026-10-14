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
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonWhirlpoolEntity;
import com.grasshopper.darkbluemoon.event.WhirlpoolStandStayHandler;

import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class DarkBlueMoonWhirlpoolAction extends StandEntityAction {

    public static final StandPose WHIRLPOOL_POSE = new StandPose("Stir");

    public DarkBlueMoonWhirlpoolAction(Builder builder) {
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
        if (user == null || !(user.isInWater() || user.isUnderWater())) {
            return ActionConditionResult.NEGATIVE;
        }

        int radius = DarkBlueMoonConfig.WHIRLPOOL_WATER_CHECK_RADIUS.get();
        int required = DarkBlueMoonConfig.WHIRLPOOL_REQUIRED_WATER.get();

        if (!hasEnoughWater(user.level, user.blockPosition(), radius, required)) {
            sendNotEnoughSpaceMessage(user);
            return ActionConditionResult.NEGATIVE;
        }

        return ActionConditionResult.POSITIVE;
    }

    private boolean hasEnoughWater(World world, BlockPos center, int radius, int required) {
        int waterCount = 0;

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius))) {
            if (world.getBlockState(pos).getMaterial() == Material.WATER) {
                waterCount++;
                if (waterCount >= required) {
                    return true;
                }
            }
        }
        return false;
    }

    private void sendNotEnoughSpaceMessage(LivingEntity user) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) user;
        if (player.level.isClientSide()) {
            return;
        }

        long time = player.level.getGameTime();
        if (player.getPersistentData().getLong("DbmWhirlpoolSpaceMsg") > time) {
            return;
        }
        player.getPersistentData().putLong("DbmWhirlpoolSpaceMsg", time + 40);

        player.displayClientMessage(
                new TranslationTextComponent("message.darkbluemoon.not_enough_space")
                        .withStyle(TextFormatting.GRAY),
                true
        );
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

        Vector3d center = user.position();
        DarkBlueMoonWhirlpoolEntity whirlpool = new DarkBlueMoonWhirlpoolEntity(world, user, center);
        world.addFreshEntity(whirlpool);

        WhirlpoolStandStayHandler.startStay(standEntity);
    }
}