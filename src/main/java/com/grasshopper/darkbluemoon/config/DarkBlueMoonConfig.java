package com.grasshopper.darkbluemoon.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DarkBlueMoonConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final ForgeConfigSpec.DoubleValue CHOP_WATER_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue CHOP_LAND_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue CHOP_WATER_RADIUS;
    public static final ForgeConfigSpec.DoubleValue CHOP_LAND_RADIUS;
    public static final ForgeConfigSpec.DoubleValue CHOP_WATER_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue CHOP_LAND_KNOCKBACK;
    public static final ForgeConfigSpec.BooleanValue CHOP_BREAK_BLOCKS;
    public static final ForgeConfigSpec.IntValue CHOP_BREAK_BLOCK_RADIUS;
    public static final ForgeConfigSpec.IntValue CHOP_DAMAGE_DELAY_TICKS;
    public static final ForgeConfigSpec.DoubleValue CHOP_BLOCK_BREAK_DOT;
    public static final ForgeConfigSpec.IntValue CHOP_BLOCK_BREAK_MAX_Y;
    public static final ForgeConfigSpec.BooleanValue CHOP_BREAK_HARD_BLOCKS;

    public static final ForgeConfigSpec.IntValue BARNACLE_BIND_DURATION;
    public static final ForgeConfigSpec.IntValue BARNACLE_TRAP_DURATION;
    public static final ForgeConfigSpec.DoubleValue BARNACLE_BIND_RADIUS;
    public static final ForgeConfigSpec.IntValue BARNACLE_MAX_COUNT;

    public static final ForgeConfigSpec.IntValue SCALE_SHOT_COUNT;
    public static final ForgeConfigSpec.DoubleValue SCALE_SHOT_WATER_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue SCALE_SHOT_LAND_DAMAGE;
    public static final ForgeConfigSpec.IntValue SCALE_SHOT_WATER_LIFESPAN;
    public static final ForgeConfigSpec.IntValue SCALE_SHOT_LAND_LIFESPAN;
    public static final ForgeConfigSpec.IntValue SCALE_SHOT_MAX_AMMO;
    public static final ForgeConfigSpec.IntValue SCALE_SHOT_REGEN_INTERVAL;
    public static final ForgeConfigSpec.IntValue SCALE_SHOT_REGEN_AMOUNT;

    public static final ForgeConfigSpec.IntValue WHIRLPOOL_CHARGE_TIME;
    public static final ForgeConfigSpec.IntValue WHIRLPOOL_LIFESPAN;
    public static final ForgeConfigSpec.DoubleValue WHIRLPOOL_RADIUS;
    public static final ForgeConfigSpec.DoubleValue WHIRLPOOL_PULL_SPEED;
    public static final ForgeConfigSpec.DoubleValue WHIRLPOOL_MIN_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue WHIRLPOOL_MAX_DAMAGE;
    public static final ForgeConfigSpec.IntValue WHIRLPOOL_WATER_CHECK_RADIUS;
    public static final ForgeConfigSpec.IntValue WHIRLPOOL_REQUIRED_WATER;

    public static final ForgeConfigSpec.IntValue BARNACLE_MIN_COST;
    public static final ForgeConfigSpec.IntValue BARNACLE_MAX_COST;
    public static final ForgeConfigSpec.IntValue BARNACLE_NECKLACE_COST_DIVISOR;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Dark Blue Moon Stand Settings").push("darkbluemoon");

        CHOP_WATER_DAMAGE = builder
                .comment("Chop - Underwater Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.chopWaterDamage")
                .defineInRange("chopWaterDamage", 30.0D, 0.0D, 1000.0D);

        CHOP_LAND_DAMAGE = builder
                .comment("Chop - Land Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.chopLandDamage")
                .defineInRange("chopLandDamage", 10.0D, 0.0D, 1000.0D);

        CHOP_WATER_RADIUS = builder
                .comment("Chop - Underwater Attack Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.chopWaterRadius")
                .defineInRange("chopWaterRadius", 3.0D, 0.0D, 32.0D);

        CHOP_LAND_RADIUS = builder
                .comment("Chop - Land Attack Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.chopLandRadius")
                .defineInRange("chopLandRadius", 1.5D, 0.0D, 32.0D);

        CHOP_WATER_KNOCKBACK = builder
                .comment("Chop - Underwater Knockback")
                .translation("darkbluemoon.configuration.darkbluemoon.chopWaterKnockback")
                .defineInRange("chopWaterKnockback", 2.5D, 0.0D, 32.0D);

        CHOP_LAND_KNOCKBACK = builder
                .comment("Chop - Land Knockback")
                .translation("darkbluemoon.configuration.darkbluemoon.chopLandKnockback")
                .defineInRange("chopLandKnockback", 0.5D, 0.0D, 32.0D);

        CHOP_BREAK_BLOCKS = builder
                .comment("Chop - Break Blocks")
                .translation("darkbluemoon.configuration.darkbluemoon.chopBreakBlocks")
                .define("chopBreakBlocks", true);

        CHOP_BREAK_BLOCK_RADIUS = builder
                .comment("Chop - Block Break Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.chopBreakBlockRadius")
                .defineInRange("chopBreakBlockRadius", 2, 0, 32);

        CHOP_BLOCK_BREAK_DOT = builder
                .comment("Chop - Block Break Front Angle")
                .translation("darkbluemoon.configuration.darkbluemoon.chopBlockBreakDot")
                .defineInRange("chopBlockBreakDot", 0.3D, -1.0D, 1.0D);

        CHOP_BLOCK_BREAK_MAX_Y = builder
                .comment("Chop - Block Break Max Height")
                .translation("darkbluemoon.configuration.darkbluemoon.chopBlockBreakMaxY")
                .defineInRange("chopBlockBreakMaxY", 1, -16, 16);

        CHOP_BREAK_HARD_BLOCKS = builder
                .comment("Chop - Break Hard Blocks")
                .translation("darkbluemoon.configuration.darkbluemoon.chopBreakHardBlocks")
                .define("chopBreakHardBlocks", false);

        CHOP_DAMAGE_DELAY_TICKS = builder
                .comment("Chop - Damage Delay Ticks")
                .translation("darkbluemoon.configuration.darkbluemoon.chopDamageDelayTicks")
                .defineInRange("chopDamageDelayTicks", 10, 0, 200);

        BARNACLE_BIND_DURATION = builder
                .comment("Barnacle - Bind Duration")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleBindDuration")
                .defineInRange("barnacleBindDuration", 200, 0, 20000);

        BARNACLE_TRAP_DURATION = builder
                .comment("Barnacle - Trap Duration")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleTrapDuration")
                .defineInRange("barnacleTrapDuration", 1200, 0, 20000);

        BARNACLE_BIND_RADIUS = builder
                .comment("Barnacle - Bind Check Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleBindRadius")
                .defineInRange("barnacleBindRadius", 3.0D, 0.0D, 32.0D);

        BARNACLE_MAX_COUNT = builder
                .comment("Barnacle - Max Entities")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleMaxCount")
                .defineInRange("barnacleMaxCount", 50, 0, 500);

        SCALE_SHOT_COUNT = builder
                .comment("Scale Shot - Shots Per Use")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotCount")
                .defineInRange("scaleShotCount", 5, 1, 100);

        SCALE_SHOT_WATER_DAMAGE = builder
                .comment("Scale Shot - Underwater Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotWaterDamage")
                .defineInRange("scaleShotWaterDamage", 5.0D, 0.0D, 1000.0D);

        SCALE_SHOT_LAND_DAMAGE = builder
                .comment("Scale Shot - Land Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotLandDamage")
                .defineInRange("scaleShotLandDamage", 2.0D, 0.0D, 1000.0D);

        SCALE_SHOT_WATER_LIFESPAN = builder
                .comment("Scale Shot - Underwater Lifespan")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotWaterLifespan")
                .defineInRange("scaleShotWaterLifespan", 100, 0, 20000);

        SCALE_SHOT_LAND_LIFESPAN = builder
                .comment("Scale Shot - Land Lifespan")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotLandLifespan")
                .defineInRange("scaleShotLandLifespan", 20, 0, 20000);

        SCALE_SHOT_MAX_AMMO = builder
                .comment("Scale Shot - Max Ammo")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotMaxAmmo")
                .defineInRange("scaleShotMaxAmmo", 20, 1, 10000);

        SCALE_SHOT_REGEN_INTERVAL = builder
                .comment("Scale Shot - Regen Interval")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotRegenInterval")
                .defineInRange("scaleShotRegenInterval", 100, 1, 20000);

        SCALE_SHOT_REGEN_AMOUNT = builder
                .comment("Scale Shot - Regen Amount")
                .translation("darkbluemoon.configuration.darkbluemoon.scaleShotRegenAmount")
                .defineInRange("scaleShotRegenAmount", 5, 1, 1000);

        WHIRLPOOL_CHARGE_TIME = builder
                .comment("Whirlpool - Charge Time")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolChargeTime")
                .defineInRange("whirlpoolChargeTime", 200, 0, 20000);

        WHIRLPOOL_LIFESPAN = builder
                .comment("Whirlpool - Lifespan")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolLifespan")
                .defineInRange("whirlpoolLifespan", 1000, 1, 100000);

        WHIRLPOOL_RADIUS = builder
                .comment("Whirlpool - Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolRadius")
                .defineInRange("whirlpoolRadius", 10.0D, 0.0D, 128.0D);

        WHIRLPOOL_PULL_SPEED = builder
                .comment("Whirlpool - Pull Speed")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolPullSpeed")
                .defineInRange("whirlpoolPullSpeed", 0.4D, 0.0D, 16.0D);

        WHIRLPOOL_MIN_DAMAGE = builder
                .comment("Whirlpool - Min Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolMinDamage")
                .defineInRange("whirlpoolMinDamage", 1.0D, 0.0D, 1000.0D);

        WHIRLPOOL_MAX_DAMAGE = builder
                .comment("Whirlpool - Max Damage")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolMaxDamage")
                .defineInRange("whirlpoolMaxDamage", 5.0D, 0.0D, 1000.0D);

        WHIRLPOOL_WATER_CHECK_RADIUS = builder
                .comment("Whirlpool - Water Check Radius")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolWaterCheckRadius")
                .defineInRange("whirlpoolWaterCheckRadius", 10, 1, 128);

        WHIRLPOOL_REQUIRED_WATER = builder
                .comment("Whirlpool - Required Water")
                .translation("darkbluemoon.configuration.darkbluemoon.whirlpoolRequiredWater")
                .defineInRange("whirlpoolRequiredWater", 200, 0, 100000);

        BARNACLE_MIN_COST = builder
                .comment("Barnacle Item - Min Cost")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleMinCost")
                .defineInRange("barnacleMinCost", 15, 0, 10000);

        BARNACLE_MAX_COST = builder
                .comment("Barnacle Item - Max Cost")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleMaxCost")
                .defineInRange("barnacleMaxCost", 20, 0, 10000);

        BARNACLE_NECKLACE_COST_DIVISOR = builder
                .comment("Barnacle Item - Necklace Cost Divisor")
                .translation("darkbluemoon.configuration.darkbluemoon.barnacleNecklaceCostDivisor")
                .defineInRange("barnacleNecklaceCostDivisor", 3, 1, 100);

        builder.pop();

        COMMON_SPEC = builder.build();
        SPEC = COMMON_SPEC;
    }
}