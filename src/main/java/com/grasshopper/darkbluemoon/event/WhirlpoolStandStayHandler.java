package com.grasshopper.darkbluemoon.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class WhirlpoolStandStayHandler {

    private static final int STAY_DURATION = 200;

    private static class StayData {
        final Vector3d pos;
        int ticksLeft;

        StayData(Vector3d pos, int ticksLeft) {
            this.pos = pos;
            this.ticksLeft = ticksLeft;
        }
    }

    private static final Map<UUID, StayData> STAYING = new HashMap<>();

    public static void startStay(StandEntity stand) {
        if (stand == null || stand.level.isClientSide()) {
            return;
        }
        stand.setManualControl(true, true);
        stand.setDeltaMovement(Vector3d.ZERO);
        STAYING.put(stand.getUUID(), new StayData(stand.position(), STAY_DURATION));
    }

    public static boolean isStaying(StandEntity stand) {
        return stand != null && STAYING.containsKey(stand.getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (STAYING.isEmpty()) {
            return;
        }

        Iterator<Map.Entry<UUID, StayData>> it = STAYING.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, StayData> entry = it.next();
            StayData data = entry.getValue();

            StandEntity stand = findStandByUUID(entry.getKey());
            if (stand == null || !stand.isAlive()) {
                it.remove();
                continue;
            }

            stand.setDeltaMovement(Vector3d.ZERO);
            stand.setPos(data.pos.x, data.pos.y, data.pos.z);
            stand.xo = data.pos.x;
            stand.yo = data.pos.y;
            stand.zo = data.pos.z;
            stand.xOld = data.pos.x;
            stand.yOld = data.pos.y;
            stand.zOld = data.pos.z;
            stand.hurtMarked = true;

            data.ticksLeft--;
            if (data.ticksLeft <= 0) {
                stand.setManualControl(false, false);
                it.remove();
            }
        }
    }

    private static StandEntity findStandByUUID(UUID uuid) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return null;
        }
        for (ServerWorld world : server.getAllLevels()) {
            Entity e = world.getEntity(uuid);
            if (e instanceof StandEntity) {
                return (StandEntity) e;
            }
        }
        return null;
    }
}