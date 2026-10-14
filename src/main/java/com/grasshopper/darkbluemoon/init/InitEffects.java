package com.grasshopper.darkbluemoon.init;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.potion.BarnacleBindEffect;
import com.grasshopper.darkbluemoon.potion.DarkBlueMoonRainEffect;
import com.grasshopper.darkbluemoon.potion.DarkBlueMoonWaterEffect;
import com.grasshopper.darkbluemoon.potion.VulnerabilityEffect;

import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
            ForgeRegistries.POTIONS, DarkBlueMoonMain.MOD_ID);

    public static final RegistryObject<Effect> DARK_BLUE_MOON_WATER_USER = EFFECTS.register(
            "dark_blue_moon_water_user",
            () -> new DarkBlueMoonWaterEffect(3.0D, "dbm_water_user"));

    public static final RegistryObject<Effect> DARK_BLUE_MOON_WATER_STAND = EFFECTS.register(
            "dark_blue_moon_water_stand",
            () -> new DarkBlueMoonWaterEffect(5.0D, "dbm_water_stand"));

    public static final RegistryObject<Effect> DARK_BLUE_MOON_RAIN_USER = EFFECTS.register(
            "dark_blue_moon_rain_user",
            DarkBlueMoonRainEffect::new);

    public static final RegistryObject<Effect> BARNACLE_BIND = EFFECTS.register(
            "barnacle_bind",
            BarnacleBindEffect::new);

    public static final RegistryObject<Effect> VULNERABILITY = EFFECTS.register(
            "vulnerability",
            VulnerabilityEffect::new);
}