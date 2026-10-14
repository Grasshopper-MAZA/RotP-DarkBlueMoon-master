package com.grasshopper.darkbluemoon.potion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.entity.BarnacleEntity;
import com.grasshopper.darkbluemoon.init.InitStands;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BarnacleBindEffect extends Effect {

    private static final Random RANDOM = new Random();
    private static final int MAX_BARNACLES = 50;
    private static final int MIN_BARNACLES = 10;

    public BarnacleBindEffect() {
        super(EffectType.HARMFUL, 0x2A4A6A);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level.isClientSide()) return;
        if (entity instanceof StandEntity) return;

        for (BarnacleEntity b : entity.level.getEntitiesOfClass(BarnacleEntity.class,
                entity.getBoundingBox().inflate(8.0D))) {
            if (b.getTarget() != entity) {
                b.remove();
            }
        }

        entity.hurt(DamageSource.MAGIC, 2.0F);

        entity.addEffect(new EffectInstance(
                Effects.MOVEMENT_SLOWDOWN,
                40, 4,
                false, false, false
        ));

        entity.addEffect(new EffectInstance(
                Effects.DIG_SLOWDOWN,
                40, 4,
                false, false, false
        ));

        Vector3d motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x, motion.y - 0.08D, motion.z);
        entity.hurtMarked = true;

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            IStandPower power = IStandPower.getPlayerStandPower(player);
            if (power != null && power.hasPower()) {
                power.setCooldownTimer(InitStands.DARK_BLUE_MOON_PUNCH.get(), 20);
                power.setCooldownTimer(InitStands.DARK_BLUE_MOON_BARRAGE.get(), 20);
                power.setCooldownTimer(InitStands.DARK_BLUE_MOON_CHOP.get(), 20);
                power.setCooldownTimer(InitStands.DARK_BLUE_MOON_SCALE_SHOT.get(), 20);
                power.setCooldownTimer(InitStands.DARK_BLUE_MOON_BARNACLE.get(), 20);
            }
        }

        int current = countBarnacles(entity.level, entity);
        if (current < MAX_BARNACLES) {
            int toSpawn = current < MIN_BARNACLES ? 3 + RANDOM.nextInt(3) : 1 + RANDOM.nextInt(3);
            int canSpawn = Math.min(toSpawn, MAX_BARNACLES - current);
            spawnBarnacles(entity.level, entity, canSpawn);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    public static void spawnBarnacles(World world, LivingEntity target, int count) {
        if (world.isClientSide()) return;
        for (int i = 0; i < count; i++) {
            Vector3d offset = randomOffsetOnBody(target);
            BarnacleEntity barnacle = new BarnacleEntity(world, target, offset);
            world.addFreshEntity(barnacle);
        }
    }

    public static int countBarnacles(World world, LivingEntity target) {
        List<BarnacleEntity> list = new ArrayList<>();
        for (BarnacleEntity b : world.getEntitiesOfClass(BarnacleEntity.class,
                target.getBoundingBox().inflate(4.0D))) {
            if (b.getTarget() == target) {
                list.add(b);
            }
        }
        return list.size();
    }

    private static Vector3d randomOffsetOnBody(LivingEntity target) {
        double angle = RANDOM.nextDouble() * Math.PI * 2;
        double radius = target.getBbWidth() * 0.5D;
        double x = Math.cos(angle) * radius;
        double z = Math.sin(angle) * radius;
        double y = RANDOM.nextDouble() * target.getBbHeight() * 0.8D + target.getBbHeight() * 0.1D;
        return new Vector3d(x, y, z);
    }
}