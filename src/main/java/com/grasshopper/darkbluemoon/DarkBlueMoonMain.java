package com.grasshopper.darkbluemoon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.grasshopper.darkbluemoon.config.DarkBlueMoonConfig;
import com.grasshopper.darkbluemoon.init.InitEffects;
import com.grasshopper.darkbluemoon.init.InitEntities;
import com.grasshopper.darkbluemoon.init.InitItems;
import com.grasshopper.darkbluemoon.init.InitSounds;
import com.grasshopper.darkbluemoon.init.InitStands;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DarkBlueMoonMain.MOD_ID)
public class DarkBlueMoonMain {
    public static final String MOD_ID = "darkbluemoon";
    public static final Logger LOGGER = LogManager.getLogger();

    public DarkBlueMoonMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);

        ModLoadingContext.get().registerConfig(
                ModConfig.Type.SERVER,
                DarkBlueMoonConfig.COMMON_SPEC,
                "darkbluemoon-server.toml");

        LOGGER.info("Dark Blue Moon addon loaded");
    }
}