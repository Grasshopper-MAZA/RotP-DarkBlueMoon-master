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
import com.grasshopper.darkbluemoon.entity.ScaleShotEntity;
import com.grasshopper.darkbluemoon.event.ScaleShotAmmoHandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DarkBlueMoonScaleShotFocusedAction extends StandEntityAction {

    public static final StandPose SCALE_SHOT_FOCUSED_POSE = new StandPose("MAZA");

    public DarkBlueMoonScaleShotFocusedAction(Builder builder) {
        super(builder);
    }

    @Override
    public ActionConditionResult checkConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        ActionConditionResult base = super.checkConditions(user, power, target);
        if (!base.isPositive()) {
            return base;
        }
        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            int required = DarkBlueMoonConfig.SCALE_SHOT_COUNT.get();
            if (ScaleShotAmmoHandler.getAmmo(player) < required) {
                if (!player.level.isClientSide()) {
                    ScaleShotAmmoHandler.sendNoAmmoMessage(player);
                }
                return ActionConditionResult.NEGATIVE;
            }
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public StandRelativeOffset getOffsetFromUser(IStandPower standPower, StandEntity standEntity, StandEntityTask task) {
        return StandRelativeOffset.withYOffset(0, 0, 1.2);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide()) {
            return;
        }

        LivingEntity user = standEntity.getUser();
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity) user;

        int count = DarkBlueMoonConfig.SCALE_SHOT_COUNT.get();

        if (!ScaleShotAmmoHandler.consumeAmmo(player, count)) {
            return;
        }

        boolean inWater = user.isInWater() || user.isUnderWater();

        for (int i = 0; i < count; i++) {
            ScaleShotEntity scale = new ScaleShotEntity(user, world, inWater);
            scale.setPos(
                    standEntity.getX(),
                    standEntity.getEyeY() - 0.2D,
                    standEntity.getZ()
            );
            scale.shootFromRotation(standEntity, 1.5F, 1.5F * i);
            world.addFreshEntity(scale);
        }
    }
}