package com.grasshopper.darkbluemoon.potion;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

public class DarkBlueMoonWaterEffect extends Effect {

    private final double atkBonus;
    private final UUID atkUuid;
    private final UUID spdUuid;
    private final UUID swimUuid;

    public DarkBlueMoonWaterEffect(double atkBonus, String uuidPrefix) {
        super(EffectType.BENEFICIAL, 0x1E3A8A);
        this.atkBonus = atkBonus;
        this.atkUuid = UUID.nameUUIDFromBytes((uuidPrefix + "_atk").getBytes(StandardCharsets.UTF_8));
        this.spdUuid = UUID.nameUUIDFromBytes((uuidPrefix + "_spd").getBytes(StandardCharsets.UTF_8));
        this.swimUuid = UUID.nameUUIDFromBytes((uuidPrefix + "_swim").getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager manager, int amplifier) {
        ModifiableAttributeInstance atk = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (atk != null) {
            atk.removeModifier(atkUuid);
            atk.addTransientModifier(new AttributeModifier(
                    atkUuid, "dbm_water_atk", atkBonus, AttributeModifier.Operation.ADDITION));
        }
        ModifiableAttributeInstance spd = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (spd != null) {
            spd.removeModifier(spdUuid);
            spd.addTransientModifier(new AttributeModifier(
                    spdUuid, "dbm_water_spd", 0.01D, AttributeModifier.Operation.ADDITION));
        }
        ModifiableAttributeInstance swim = entity.getAttribute(ForgeMod.SWIM_SPEED.get());
        if (swim != null) {
            swim.removeModifier(swimUuid);
            swim.addTransientModifier(new AttributeModifier(
                    swimUuid, "dbm_water_swim", 0.5D, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager manager, int amplifier) {
        ModifiableAttributeInstance atk = entity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (atk != null) {
            atk.removeModifier(atkUuid);
        }
        ModifiableAttributeInstance spd = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (spd != null) {
            spd.removeModifier(spdUuid);
        }
        ModifiableAttributeInstance swim = entity.getAttribute(ForgeMod.SWIM_SPEED.get());
        if (swim != null) {
            swim.removeModifier(swimUuid);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}