package com.grasshopper.darkbluemoon.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleCancelAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnaclePullAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleSelfAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleSelfCancelAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleTrapAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarnacleTrapCancelAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonBarrage;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonChopAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonMiniTornadoAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonScaleShotAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonScaleShotFocusedAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonWhirlpoolAction;
import com.grasshopper.darkbluemoon.action.DarkBlueMoonWhirlpoolCancelAction;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), DarkBlueMoonMain.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), DarkBlueMoonMain.MOD_ID);

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_PUNCH = ACTIONS.register("dark_blue_moon_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.DARK_BLUE_MOON_PUNCH_LIGHT)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARRAGE = ACTIONS.register("dark_blue_moon_barrage",
            () -> new DarkBlueMoonBarrage(new StandEntityMeleeBarrage.Builder()
                    .standSound(StandEntityAction.Phase.PERFORM, false, InitSounds.DARK_BLUE_MOON_PUNCH_BARRAGE)
                    .barrageHitSound(null)
                    .standPose(StandPose.BARRAGE)
                    .standUserWalkSpeed(1F)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_CHOP = ACTIONS.register("dark_blue_moon_chop",
            () -> new DarkBlueMoonChopAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonChopAction.CHOP_POSE)
                    .standPerformDuration(20)
                    .staminaCost(50)
                    .cooldown(400)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_SCALE_SHOT = ACTIONS.register("dark_blue_moon_scale_shot",
            () -> new DarkBlueMoonScaleShotAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonScaleShotAction.SCALE_SHOT_POSE)
                    .standPerformDuration(13)
                    .staminaCost(100)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_SCALE_SHOT_FOCUSED = ACTIONS.register("dark_blue_moon_scale_shot_focused",
            () -> new DarkBlueMoonScaleShotFocusedAction(new StandEntityAction.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_SCALE_SHOT)
                    .standPose(DarkBlueMoonScaleShotFocusedAction.SCALE_SHOT_FOCUSED_POSE)
                    .standPerformDuration(13)
                    .staminaCost(100)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE = ACTIONS.register("dark_blue_moon_barnacle",
            () -> new DarkBlueMoonBarnacleAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonBarnacleAction.BARNACLE_POSE)
                    .standPerformDuration(20)
                    .staminaCost(200)
                    .cooldown(40)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_CANCEL = ACTIONS.register("dark_blue_moon_barnacle_cancel",
            () -> new DarkBlueMoonBarnacleCancelAction(new StandEntityAction.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_BARNACLE)
                    .standPose(DarkBlueMoonBarnacleCancelAction.BARNACLE_CANCEL_POSE)
                    .standPerformDuration(10)
                    .staminaCost(0)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_SELF = ACTIONS.register("dark_blue_moon_barnacle_self",
            () -> new DarkBlueMoonBarnacleSelfAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonBarnacleSelfAction.BARNACLE_SELF_POSE)
                    .standPerformDuration(20)
                    .staminaCost(200)
                    .cooldown(40)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_SELF_CANCEL = ACTIONS.register("dark_blue_moon_barnacle_self_cancel",
            () -> new DarkBlueMoonBarnacleSelfCancelAction(new StandEntityAction.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_BARNACLE_SELF)
                    .standPose(DarkBlueMoonBarnacleSelfCancelAction.BARNACLE_SELF_CANCEL_POSE)
                    .standPerformDuration(10)
                    .staminaCost(0)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_PULL = ACTIONS.register("dark_blue_moon_barnacle_pull",
            () -> new DarkBlueMoonBarnaclePullAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonBarnaclePullAction.BARNACLE_PULL_POSE)
                    .standPerformDuration(10)
                    .staminaCost(100)
                    .cooldown(60)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_TRAP = ACTIONS.register("dark_blue_moon_barnacle_trap",
            () -> new DarkBlueMoonBarnacleTrapAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonBarnacleTrapAction.BARNACLE_TRAP_POSE)
                    .standPerformDuration(10)
                    .staminaCost(150)
                    .cooldown(40)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BARNACLE_TRAP_CANCEL = ACTIONS.register("dark_blue_moon_barnacle_trap_cancel",
            () -> new DarkBlueMoonBarnacleTrapCancelAction(new StandEntityAction.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_BARNACLE_TRAP)
                    .standPose(DarkBlueMoonBarnacleTrapCancelAction.BARNACLE_TRAP_CANCEL_POSE)
                    .standPerformDuration(10)
                    .staminaCost(0)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_WHIRLPOOL = ACTIONS.register("dark_blue_moon_whirlpool",
            () -> new DarkBlueMoonWhirlpoolAction(new StandEntityAction.Builder()
                    .standPose(DarkBlueMoonWhirlpoolAction.WHIRLPOOL_POSE)
                    .standPerformDuration(200)
                    .staminaCost(500)
                    .cooldown(600)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_WHIRLPOOL_CANCEL = ACTIONS.register("dark_blue_moon_whirlpool_cancel",
            () -> new DarkBlueMoonWhirlpoolCancelAction(new StandEntityAction.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_WHIRLPOOL)
                    .standPose(DarkBlueMoonWhirlpoolCancelAction.WHIRLPOOL_CANCEL_POSE)
                    .standPerformDuration(10)
                    .staminaCost(0)
                    .cooldown(20)
                    .resolveLevelToUnlock(0)));

    public static final RegistryObject<StandEntityHeavyAttack> DARK_BLUE_MOON_FINISHER_PUNCH = ACTIONS.register("dark_blue_moon_finisher_punch",
            () -> new DarkBlueMoonMiniTornadoAction(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.DARK_BLUE_MOON_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> DARK_BLUE_MOON_HEAVY_PUNCH = ACTIONS.register("dark_blue_moon_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .shiftVariationOf(DARK_BLUE_MOON_PUNCH).shiftVariationOf(DARK_BLUE_MOON_BARRAGE)
                    .setFinisherVariation(DARK_BLUE_MOON_FINISHER_PUNCH)
                    .punchSound(InitSounds.DARK_BLUE_MOON_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> DARK_BLUE_MOON_BLOCK = ACTIONS.register("dark_blue_moon_block",
            () -> new StandEntityBlock());

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<DarkBlueMoonEntity>> STAND_DARK_BLUE_MOON =
            new EntityStandRegistryObject<>("dark_blue_moon",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                            .color(0x1E3A8A)
                            .storyPartName(ModStandsInit.PART_3_NAME)
                            .leftClickHotbar(
                                    DARK_BLUE_MOON_PUNCH.get(),
                                    DARK_BLUE_MOON_BARRAGE.get(),
                                    DARK_BLUE_MOON_CHOP.get(),
                                    DARK_BLUE_MOON_SCALE_SHOT.get()
                            )
                            .rightClickHotbar(
                                    DARK_BLUE_MOON_BLOCK.get(),
                                    DARK_BLUE_MOON_BARNACLE.get(),
                                    DARK_BLUE_MOON_BARNACLE_SELF.get(),
                                    DARK_BLUE_MOON_BARNACLE_PULL.get(),
                                    DARK_BLUE_MOON_BARNACLE_TRAP.get(),
                                    DARK_BLUE_MOON_WHIRLPOOL.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .tier(6)
                                    .power(10)
                                    .speed(10)
                                    .range(5, 15)
                                    .durability(12)
                                    .precision(10)
                                    .build())
                            .addSummonShout(InitSounds.DARK_BLUE_MOON_SUMMON_VOICELINE)
                            .addOst(InitSounds.DARK_BLUE_MOON_OST)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<DarkBlueMoonEntity>(DarkBlueMoonEntity::new, 0.7F, 2.1F)
                            .summonSound(InitSounds.DARK_BLUE_MOON_SUMMON_SOUND)
                            .unsummonSound(InitSounds.DARK_BLUE_MOON_UNSUMMON_SOUND))
                    .withDefaultStandAttributes();
}