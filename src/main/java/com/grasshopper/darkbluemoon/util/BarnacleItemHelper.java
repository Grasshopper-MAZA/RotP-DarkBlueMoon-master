package com.grasshopper.darkbluemoon.util;

import java.util.Random;

import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.config.DarkBlueMoonConfig;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;
import com.grasshopper.darkbluemoon.init.InitItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class BarnacleItemHelper {

    private static final Random RANDOM = new Random();

    public static boolean hasDarkBlueMoon(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entity;
        IStandPower power = IStandPower.getPlayerStandPower(player);
        if (power == null || !power.hasPower()) return false;
        IStandManifestation stand = power.getStandManifestation();
        return stand instanceof DarkBlueMoonEntity;
    }

    public static boolean isHoldingBarnacle(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entity;
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        return main.getItem() == InitItems.BARNACLE.get()
                || off.getItem() == InitItems.BARNACLE.get();
    }

    public static boolean hasBarnacleNecklace(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entity;
        ItemStack necklace = player.getItemBySlot(EquipmentSlotType.CHEST);
        return necklace.getItem() == InitItems.BARNACLE_NECKLACE.get();
    }

    public static int countBarnacle(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity)) return 0;
        PlayerEntity player = (PlayerEntity) entity;
        int count = 0;
        for (ItemStack stack : player.inventory.items) {
            if (stack.getItem() == InitItems.BARNACLE.get()) {
                count += stack.getCount();
            }
        }
        ItemStack off = player.getOffhandItem();
        if (off.getItem() == InitItems.BARNACLE.get()) {
            count += off.getCount();
        }
        return count;
    }

    public static int getRandomCost(LivingEntity entity) {
        int min = DarkBlueMoonConfig.BARNACLE_MIN_COST.get();
        int max = DarkBlueMoonConfig.BARNACLE_MAX_COST.get();
        int base = min + RANDOM.nextInt(Math.max(1, max - min + 1));
        if (hasBarnacleNecklace(entity)) {
            int divisor = DarkBlueMoonConfig.BARNACLE_NECKLACE_COST_DIVISOR.get();
            base = Math.max(1, base / divisor);
        }
        return base;
    }

    public static boolean consumeBarnacle(LivingEntity entity, int amount) {
        if (!(entity instanceof PlayerEntity)) return false;
        PlayerEntity player = (PlayerEntity) entity;
        if (countBarnacle(player) < amount) return false;

        int remaining = amount;
        for (ItemStack stack : player.inventory.items) {
            if (stack.getItem() == InitItems.BARNACLE.get()) {
                int take = Math.min(stack.getCount(), remaining);
                stack.shrink(take);
                remaining -= take;
                if (remaining <= 0) break;
            }
        }
        if (remaining > 0) {
            ItemStack off = player.getOffhandItem();
            if (off.getItem() == InitItems.BARNACLE.get()) {
                int take = Math.min(off.getCount(), remaining);
                off.shrink(take);
            }
        }
        return true;
    }

    public static boolean canUseBarnacleSkill(LivingEntity user) {
        if (user.isInWater() || user.isUnderWater()) {
            return true;
        }
        return hasDarkBlueMoon(user) && isHoldingBarnacle(user)
                && countBarnacle(user) >= DarkBlueMoonConfig.BARNACLE_MIN_COST.get();
    }
}