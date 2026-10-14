package com.grasshopper.darkbluemoon.init;

import com.github.standobyte.jojo.JojoMod;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.item.BarnacleItem;
import com.grasshopper.darkbluemoon.item.BarnacleMeatItem;
import com.grasshopper.darkbluemoon.item.BarnacleNecklaceItem;
import com.grasshopper.darkbluemoon.item.BarnacleShellItem;
import com.grasshopper.darkbluemoon.item.CookedBarnacleItem;
import com.grasshopper.darkbluemoon.item.CookedBarnacleShellItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, DarkBlueMoonMain.MOD_ID);

    public static final RegistryObject<Item> BARNACLE = ITEMS.register("barnacle",
            () -> new BarnacleItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)));

    public static final RegistryObject<Item> BARNACLE_MEAT = ITEMS.register("barnacle_meat",
            () -> new BarnacleMeatItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)
                    .food(new net.minecraft.item.Food.Builder()
                            .nutrition(2)
                            .saturationMod(0.3F)
                            .meat()
                            .build())));

    public static final RegistryObject<Item> BARNACLE_SHELL = ITEMS.register("barnacle_shell",
            () -> new BarnacleShellItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)));

    public static final RegistryObject<Item> COOKED_BARNACLE = ITEMS.register("cooked_barnacle",
            () -> new CookedBarnacleItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)
                    .food(new net.minecraft.item.Food.Builder()
                            .nutrition(4)
                            .saturationMod(0.6F)
                            .meat()
                            .build())));

    public static final RegistryObject<Item> COOKED_BARNACLE_SHELL = ITEMS.register("cooked_barnacle_shell",
            () -> new CookedBarnacleShellItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)));

    public static final RegistryObject<Item> COOKED_BARNACLE_MEAT = ITEMS.register("cooked_barnacle_meat",
            () -> new Item(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(64)
                    .food(new net.minecraft.item.Food.Builder()
                            .nutrition(5)
                            .saturationMod(0.8F)
                            .meat()
                            .build())));

    public static final RegistryObject<Item> BARNACLE_NECKLACE = ITEMS.register("barnacle_necklace",
            () -> new BarnacleNecklaceItem(new Item.Properties().tab(JojoMod.MAIN_TAB).stacksTo(1)));
}