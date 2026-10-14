package com.grasshopper.darkbluemoon.event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.grasshopper.darkbluemoon.entity.BarnacleEntity;
import com.grasshopper.darkbluemoon.init.InitEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BarnacleBindHandler {

    private static final Map<UUID, UUID> TARGET_TO_USER = new HashMap<>();
    private static final Map<UUID, UUID> USER_TO_TARGET = new HashMap<>();

    public static void bind(LivingEntity user, LivingEntity target) {
        TARGET_TO_USER.put(target.getUUID(), user.getUUID());
        USER_TO_TARGET.put(user.getUUID(), target.getUUID());
    }

    public static void unbind(LivingEntity user) {
        if (user == null) return;
        UUID targetId = USER_TO_TARGET.remove(user.getUUID());
        if (targetId != null) {
            TARGET_TO_USER.remove(targetId);
        }
    }

    public static boolean isBinding(LivingEntity user) {
        return user != null && USER_TO_TARGET.containsKey(user.getUUID());
    }

    public static LivingEntity getTarget(LivingEntity user, World world) {
        if (user == null) return null;
        UUID targetId = USER_TO_TARGET.get(user.getUUID());
        if (targetId == null) return null;
        return world.getEntitiesOfClass(LivingEntity.class,
                        user.getBoundingBox().inflate(64.0D)).stream()
                .filter(e -> e.getUUID().equals(targetId))
                .findFirst().orElse(null);
    }

    public static void removeAllBarnacles(World world, LivingEntity target) {
        if (world.isClientSide() || target == null) return;
        for (BarnacleEntity b : world.getEntitiesOfClass(BarnacleEntity.class,
                target.getBoundingBox().inflate(8.0D))) {
            if (b.getTarget() == target) {
                b.remove();
            }
        }
        target.removeEffect(InitEffects.BARNACLE_BIND.get());
    }
}