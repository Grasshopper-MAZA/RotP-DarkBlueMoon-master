package com.grasshopper.darkbluemoon.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;
import com.grasshopper.darkbluemoon.init.InitEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class BarnacleTrapTickHandler {

    private static final Map<UUID, Integer> TRAP_TARGET_TIMER = new HashMap<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.world.isClientSide()) return;

        World world = event.world;

        for (PlayerEntity player : world.players()) {
            IStandPower power = IStandPower.getPlayerStandPower(player);
            if (power == null || !power.hasPower()) continue;
            IStandManifestation stand = power.getStandManifestation();
            if (!(stand instanceof DarkBlueMoonEntity)) continue;

            DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) stand;
            if (!dbm.hasBarnacleTrap()) continue;

            BlockPos trapPos = dbm.getBarnacleTrapPos();
            AxisAlignedBB checkBox = new AxisAlignedBB(trapPos).inflate(1.0D);

            List<LivingEntity> targets = world.getEntitiesOfClass(
                    LivingEntity.class, checkBox,
                    e -> e.isAlive());

            if (!targets.isEmpty()) {
                boolean inWater = world.getBlockState(trapPos).getMaterial().isLiquid();
                if (inWater) {
                    applyBind(targets);
                } else {
                    int delay = dbm.getPersistentData().getInt("BarnacleTrapDelay") + 1;
                    dbm.getPersistentData().putInt("BarnacleTrapDelay", delay);
                    if (delay >= 10) {
                        applyBind(targets);
                    }
                }
            } else {
                dbm.getPersistentData().putInt("BarnacleTrapDelay", 0);
            }
        }

        for (PlayerEntity player : world.players()) {
            if (!player.hasEffect(InitEffects.BARNACLE_BIND.get())) {
                TRAP_TARGET_TIMER.remove(player.getUUID());
                continue;
            }

            boolean onTrap = isOnAnyTrap(world, player);

            if (onTrap) {
                TRAP_TARGET_TIMER.put(player.getUUID(), 0);
            } else {
                int timer = TRAP_TARGET_TIMER.getOrDefault(player.getUUID(), 0) + 1;
                if (timer >= 40) {
                    player.removeEffect(InitEffects.BARNACLE_BIND.get());
                    BarnacleBindHandler.removeAllBarnacles(world, player);
                    TRAP_TARGET_TIMER.remove(player.getUUID());
                } else {
                    TRAP_TARGET_TIMER.put(player.getUUID(), timer);
                }
            }
        }
    }

    private static boolean isOnAnyTrap(World world, LivingEntity entity) {
        for (PlayerEntity player : world.players()) {
            IStandPower power = IStandPower.getPlayerStandPower(player);
            if (power == null || !power.hasPower()) continue;
            IStandManifestation stand = power.getStandManifestation();
            if (!(stand instanceof DarkBlueMoonEntity)) continue;
            DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) stand;
            if (!dbm.hasBarnacleTrap()) continue;
            BlockPos trapPos = dbm.getBarnacleTrapPos();
            AxisAlignedBB box = new AxisAlignedBB(trapPos).inflate(1.0D);
            if (box.intersects(entity.getBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    private static void applyBind(List<LivingEntity> targets) {
        for (LivingEntity target : targets) {
            if (!target.hasEffect(InitEffects.BARNACLE_BIND.get())) {
                target.addEffect(new EffectInstance(
                        InitEffects.BARNACLE_BIND.get(),
                        600, 0,
                        false, false, false
                ));
            }
        }
    }
}