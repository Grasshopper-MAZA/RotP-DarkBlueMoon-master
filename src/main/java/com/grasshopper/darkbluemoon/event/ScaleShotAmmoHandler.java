package com.grasshopper.darkbluemoon.event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class ScaleShotAmmoHandler {

    private static final int MAX_AMMO = 20;
    private static final int REGEN_INTERVAL_TICKS = 100;
    private static final int REGEN_AMOUNT = 5;

    private static final Map<UUID, Integer> ammoMap = new HashMap<>();
    private static final Map<UUID, Integer> regenTimerMap = new HashMap<>();

    public static int getAmmo(PlayerEntity player) {
        return ammoMap.getOrDefault(player.getUUID(), MAX_AMMO);
    }

    public static boolean consumeAmmo(PlayerEntity player, int amount) {
        int current = getAmmo(player);
        if (current < amount) {
            return false;
        }
        ammoMap.put(player.getUUID(), current - amount);
        return true;
    }

    public static void addAmmo(PlayerEntity player, int amount) {
        int current = getAmmo(player);
        ammoMap.put(player.getUUID(), Math.min(current + amount, MAX_AMMO));
    }

    public static void sendNoAmmoMessage(PlayerEntity player) {
        player.displayClientMessage(
                new TranslationTextComponent("message.darkbluemoon.no_scales")
                        .withStyle(TextFormatting.GRAY),
                true
        );
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        PlayerEntity player = event.player;
        if (player.level.isClientSide()) {
            return;
        }

        UUID id = player.getUUID();
        int timer = regenTimerMap.getOrDefault(id, 0) + 1;
        if (timer >= REGEN_INTERVAL_TICKS) {
            timer = 0;
            addAmmo(player, REGEN_AMOUNT);
        }
        regenTimerMap.put(id, timer);
    }
}