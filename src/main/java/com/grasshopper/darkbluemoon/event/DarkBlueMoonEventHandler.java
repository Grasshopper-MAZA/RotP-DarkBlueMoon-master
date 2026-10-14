package com.grasshopper.darkbluemoon.event;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.init.InitEffects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class DarkBlueMoonEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        PlayerEntity player = event.player;
        if (player.level.isClientSide()) {
            return;
        }

        boolean hasDbm = hasDarkBlueMoon(player);
        boolean inWater = hasDbm && player.isAlive()
                && (player.isInWater() || player.isUnderWater());
        boolean inRain = hasDbm && player.isAlive()
                && !inWater
                && player.level.isRaining()
                && player.level.canSeeSky(player.blockPosition());

        if (inWater) {
            player.addEffect(new EffectInstance(
                    InitEffects.DARK_BLUE_MOON_WATER_USER.get(),
                    40, 0, false, false, true));
            player.addEffect(new EffectInstance(
                    Effects.WATER_BREATHING,
                    7440, 0, false, false, true));
            player.addEffect(new EffectInstance(
                    Effects.DOLPHINS_GRACE,
                    40, 2, false, false, true));
            player.addEffect(new EffectInstance(
                    Effects.NIGHT_VISION,
                    400, 0, false, false, true));
            player.addEffect(new EffectInstance(
                    Effects.DIG_SPEED,
                    40, 0, false, false, true));
            player.removeEffect(InitEffects.DARK_BLUE_MOON_RAIN_USER.get());
        } else if (inRain) {
            player.addEffect(new EffectInstance(
                    InitEffects.DARK_BLUE_MOON_RAIN_USER.get(),
                    40, 0, false, false, true));
            player.removeEffect(InitEffects.DARK_BLUE_MOON_WATER_USER.get());
            player.removeEffect(Effects.WATER_BREATHING);
            player.removeEffect(Effects.DOLPHINS_GRACE);
            player.removeEffect(Effects.NIGHT_VISION);
            player.removeEffect(Effects.DIG_SPEED);
        } else {
            player.removeEffect(InitEffects.DARK_BLUE_MOON_WATER_USER.get());
            player.removeEffect(InitEffects.DARK_BLUE_MOON_RAIN_USER.get());
            player.removeEffect(Effects.WATER_BREATHING);
            player.removeEffect(Effects.DOLPHINS_GRACE);
            player.removeEffect(Effects.NIGHT_VISION);
            player.removeEffect(Effects.DIG_SPEED);
        }
    }

    private static boolean hasDarkBlueMoon(PlayerEntity player) {
        IStandPower power = IStandPower.getPlayerStandPower(player);
        if (power == null || !power.hasPower() || power.getType() == null) {
            return false;
        }
        return power.getType().getRegistryName() != null
                && "darkbluemoon:dark_blue_moon".equals(power.getType().getRegistryName().toString());
    }
}