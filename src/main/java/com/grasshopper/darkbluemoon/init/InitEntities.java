package com.grasshopper.darkbluemoon.init;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.BarnacleEntity;
import com.grasshopper.darkbluemoon.entity.BarnaclePullEntity;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonMiniTornadoEntity;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonWhirlpoolEntity;
import com.grasshopper.darkbluemoon.entity.ScaleShotEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, DarkBlueMoonMain.MOD_ID);

    public static final RegistryObject<EntityType<ScaleShotEntity>> SCALE_SHOT = ENTITIES.register(
            "scale_shot",
            () -> EntityType.Builder.<ScaleShotEntity>of(ScaleShotEntity::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "scale_shot").toString()));

    public static final RegistryObject<EntityType<BarnacleEntity>> BARNACLE = ENTITIES.register(
            "barnacle",
            () -> EntityType.Builder.<BarnacleEntity>of(BarnacleEntity::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "barnacle").toString()));

    public static final RegistryObject<EntityType<BarnaclePullEntity>> BARNACLE_PULL = ENTITIES.register(
            "barnacle_pull",
            () -> EntityType.Builder.<BarnaclePullEntity>of(BarnaclePullEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "barnacle_pull").toString()));

    public static final RegistryObject<EntityType<DarkBlueMoonWhirlpoolEntity>> WHIRLPOOL = ENTITIES.register(
            "whirlpool",
            () -> EntityType.Builder.<DarkBlueMoonWhirlpoolEntity>of(DarkBlueMoonWhirlpoolEntity::new, EntityClassification.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(10)
                    .updateInterval(1)
                    .build(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "whirlpool").toString()));

    public static final RegistryObject<EntityType<DarkBlueMoonMiniTornadoEntity>> MINI_WATER_TORNADO = ENTITIES.register(
            "mini_water_tornado",
            () -> EntityType.Builder.<DarkBlueMoonMiniTornadoEntity>of(DarkBlueMoonMiniTornadoEntity::new, EntityClassification.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(8)
                    .updateInterval(1)
                    .build(new ResourceLocation(DarkBlueMoonMain.MOD_ID, "mini_water_tornado").toString()));
}