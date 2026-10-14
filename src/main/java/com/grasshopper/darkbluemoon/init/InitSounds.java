package com.grasshopper.darkbluemoon.init;

import java.util.function.Supplier;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, DarkBlueMoonMain.MOD_ID);

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_SUMMON_VOICELINE = SOUNDS.register(
            "dark_blue_moon_summon_voiceline",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_summon_voiceline")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_HIT_WATER = SOUNDS.register(
            "dark_blue_moon_chop_hit_water",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_hit_water")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_HIT_LAND = SOUNDS.register(
            "dark_blue_moon_chop_hit_land",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_hit_land")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_WATER_1 = SOUNDS.register(
            "dark_blue_moon_chop_use_water_1",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_water_1")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_WATER_2 = SOUNDS.register(
            "dark_blue_moon_chop_use_water_2",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_water_2")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_WATER_3 = SOUNDS.register(
            "dark_blue_moon_chop_use_water_3",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_water_3")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_LAND_1 = SOUNDS.register(
            "dark_blue_moon_chop_use_land_1",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_land_1")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_LAND_2 = SOUNDS.register(
            "dark_blue_moon_chop_use_land_2",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_land_2")));

    public static final RegistryObject<SoundEvent> DARK_BLUE_MOON_CHOP_USE_LAND_3 = SOUNDS.register(
            "dark_blue_moon_chop_use_land_3",
            () -> new SoundEvent(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_chop_use_land_3")));

    public static final Supplier<SoundEvent> DARK_BLUE_MOON_SUMMON_SOUND = ModSounds.STAND_SUMMON_DEFAULT;
    public static final Supplier<SoundEvent> DARK_BLUE_MOON_UNSUMMON_SOUND = ModSounds.STAND_UNSUMMON_DEFAULT;
    public static final Supplier<SoundEvent> DARK_BLUE_MOON_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    public static final Supplier<SoundEvent> DARK_BLUE_MOON_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    public static final Supplier<SoundEvent> DARK_BLUE_MOON_PUNCH_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;

    public static final OstSoundList DARK_BLUE_MOON_OST = new OstSoundList(
            new ResourceLocation(DarkBlueMoonMain.MOD_ID, "dark_blue_moon_ost"), SOUNDS);
}