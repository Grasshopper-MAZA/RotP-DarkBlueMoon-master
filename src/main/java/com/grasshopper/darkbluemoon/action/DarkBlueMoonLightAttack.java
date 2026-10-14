package com.grasshopper.darkbluemoon.action;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.init.InitSounds;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class DarkBlueMoonLightAttack extends StandEntityLightAttack {

    public DarkBlueMoonLightAttack(Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        super.standPerform(world, standEntity, userPower, task);

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
}