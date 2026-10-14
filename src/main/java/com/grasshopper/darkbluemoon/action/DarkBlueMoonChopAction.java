package com.grasshopper.darkbluemoon.action;

import java.util.List;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.config.DarkBlueMoonConfig;
import com.grasshopper.darkbluemoon.init.InitEffects;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DarkBlueMoonChopAction extends StandEntityAction {

    public static final StandPose CHOP_POSE = new StandPose("chop");

    private boolean damageDealt = false;

    public DarkBlueMoonChopAction(Builder builder) {
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

        damageDealt = false;

        LivingEntity user = standEntity.getUser();
        if (user == null) {
            return;
        }

        ActionTarget aimed = standEntity.aimWithThisOrUser(
                standEntity.getAimDistance(user), ActionTarget.EMPTY, false);
        if (aimed.getType() != ActionTarget.TargetType.EMPTY) {
            standEntity.setTaskTarget(aimed);
        }

        boolean inWater = user.isInWater() || user.isUnderWater();
        SoundEvent[] useSounds;
        if (inWater) {
            useSounds = new SoundEvent[] {
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_1.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_2.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_WATER_3.get()
            };
        } else {
            useSounds = new SoundEvent[] {
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_LAND_1.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_LAND_2.get(),
                    InitSounds.DARK_BLUE_MOON_CHOP_USE_LAND_3.get()
            };
        }
        SoundEvent chosen = useSounds[world.random.nextInt(useSounds.length)];
        standEntity.playSound(chosen, 1.0F, 1.0F);
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (world.isClientSide() || damageDealt) return;

        int delay = DarkBlueMoonConfig.CHOP_DAMAGE_DELAY_TICKS.get();
        if (task.getTick() >= delay) {
            damageDealt = true;
            dealChopDamage(world, standEntity, userPower, task);
        }
    }

    private void dealChopDamage(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        LivingEntity user = standEntity.getUser();
        if (user == null) return;

        boolean inWater = user.isInWater() || user.isUnderWater();

        float damage = (float) (double) (inWater
                ? DarkBlueMoonConfig.CHOP_WATER_DAMAGE.get()
                : DarkBlueMoonConfig.CHOP_LAND_DAMAGE.get());

        double radius = inWater
                ? DarkBlueMoonConfig.CHOP_WATER_RADIUS.get()
                : DarkBlueMoonConfig.CHOP_LAND_RADIUS.get();

        double knockback = inWater
                ? DarkBlueMoonConfig.CHOP_WATER_KNOCKBACK.get()
                : DarkBlueMoonConfig.CHOP_LAND_KNOCKBACK.get();

        Vector3d look = user.getLookAngle();
        Vector3d start = standEntity.position().add(0.0D, standEntity.getBbHeight() * 0.5D, 0.0D);

        boolean hitAny = false;

        ActionTarget aimed = standEntity.aimWithThisOrUser(
                standEntity.getAimDistance(user), task.getTarget(), false);

        if (aimed.getType() == ActionTarget.TargetType.ENTITY) {
            net.minecraft.entity.Entity targetEntity = aimed.getEntity();
            if (targetEntity instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) targetEntity;
                if (target != user && target != standEntity && target.isAlive()) {
                    Vector3d toTarget = target.position().subtract(start);
                    double dist = toTarget.length();
                    if (dist <= radius && toTarget.normalize().dot(look) >= 0.3D) {
                        hitAny |= hitTarget(target, user, look, damage, knockback, inWater);
                    }
                }
            }
        }

        if (!hitAny) {
            List<LivingEntity> targets = world.getEntitiesOfClass(LivingEntity.class,
                    standEntity.getBoundingBox().inflate(radius),
                    e -> e != user && e != standEntity && e.isAlive());

            for (LivingEntity target : targets) {
                Vector3d toTarget = target.position().subtract(start);
                double dist = toTarget.length();
                if (dist > radius) continue;
                double dot = toTarget.normalize().dot(look);
                if (dot < 0.3D) continue;

                hitAny |= hitTarget(target, user, look, damage, knockback, inWater);
                break;
            }
        }

        if (!hitAny && DarkBlueMoonConfig.CHOP_BREAK_BLOCKS.get()) {
            boolean broke = breakBlocksInFront(world, standEntity, user, look, radius);
            if (broke) hitAny = true;
        }

        if (hitAny) {
            if (inWater) {
                standEntity.playSound(InitSounds.DARK_BLUE_MOON_CHOP_HIT_WATER.get(), 1.0F, 1.0F);
            } else {
                standEntity.playSound(InitSounds.DARK_BLUE_MOON_CHOP_HIT_LAND.get(), 1.0F, 1.0F);
            }
        }
    }

    private boolean hitTarget(LivingEntity target, LivingEntity user, Vector3d look,
                              float damage, double knockback, boolean inWater) {
        DamageSource source = DamageSource.mobAttack(user);
        boolean hurt = target.hurt(source, damage);
        if (hurt) {
            target.setDeltaMovement(target.getDeltaMovement().add(
                    look.x * knockback,
                    inWater ? 0.5D : 0.2D,
                    look.z * knockback
            ));
            target.hurtMarked = true;

            if (inWater) {
                target.addEffect(new EffectInstance(
                        InitEffects.VULNERABILITY.get(),
                        200, 0,
                        false, false, false));
                target.addEffect(new EffectInstance(
                        Effects.MOVEMENT_SLOWDOWN,
                        200, 0,
                        false, false, false));
            }
        }
        return hurt;
    }

    private boolean breakBlocksInFront(World world, StandEntity standEntity, LivingEntity user,
                                       Vector3d look, double radius) {
        boolean broke = false;
        BlockPos center = standEntity.blockPosition();
        int r = DarkBlueMoonConfig.CHOP_BREAK_BLOCK_RADIUS.get();
        int maxY = DarkBlueMoonConfig.CHOP_BLOCK_BREAK_MAX_Y.get();
        double dotThreshold = DarkBlueMoonConfig.CHOP_BLOCK_BREAK_DOT.get();
        boolean breakHard = DarkBlueMoonConfig.CHOP_BREAK_HARD_BLOCKS.get();

        for (int x = -r; x <= r; x++) {
            for (int y = -maxY; y <= maxY; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = world.getBlockState(pos);

                    if (state.isAir()) continue;
                    if (!breakHard && state.getDestroySpeed(world, pos) < 0) continue;

                    Vector3d toBlock = new Vector3d(
                            pos.getX() + 0.5D - standEntity.getX(),
                            pos.getY() + 0.5D - standEntity.getY(),
                            pos.getZ() + 0.5D - standEntity.getZ()
                    );
                    if (toBlock.length() > radius) continue;
                    if (toBlock.normalize().dot(look) < dotThreshold) continue;

                    world.destroyBlock(pos, false);
                    broke = true;
                }
            }
        }
        return broke;
    }
}