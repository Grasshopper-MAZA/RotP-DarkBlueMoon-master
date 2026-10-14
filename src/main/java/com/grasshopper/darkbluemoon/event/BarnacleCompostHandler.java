package com.grasshopper.darkbluemoon.event;

import java.lang.reflect.Field;
import java.util.Map;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.init.InitItems;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class BarnacleCompostHandler {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostable(InitItems.BARNACLE_SHELL.get(), 0.3F);
            registerCompostable(InitItems.COOKED_BARNACLE_SHELL.get(), 0.3F);
            registerCompostable(InitItems.BARNACLE_MEAT.get(), 0.3F);
            registerCompostable(InitItems.COOKED_BARNACLE_MEAT.get(), 0.5F);
        });
    }

    @SuppressWarnings("unchecked")
    private static void registerCompostable(Item item, float chance) {
        try {
            Field field = ComposterBlock.class.getDeclaredField("COMPOSTABLES");
            field.setAccessible(true);
            Map<Item, Float> map = (Map<Item, Float>) field.get(null);
            map.put(item, chance);
        } catch (Exception e) {
            DarkBlueMoonMain.LOGGER.error("Failed to register compostable: " + item, e);
        }
    }
}