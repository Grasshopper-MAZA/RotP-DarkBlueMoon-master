package com.grasshopper.darkbluemoon.item;

import java.util.Random;

import com.grasshopper.darkbluemoon.init.InitItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class CookedBarnacleItem extends Item {

    private static final Random RANDOM = new Random();

    public CookedBarnacleItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 100;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return ActionResult.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isClientSide()) {
            if (RANDOM.nextFloat() < 0.1F) {
                entity.addEffect(new EffectInstance(Effects.CONFUSION, 200, 0));
            }
            if (RANDOM.nextFloat() < 0.1F) {
                entity.addEffect(new EffectInstance(Effects.POISON, 100, 0));
            }
            entity.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.instabuild) {
                stack.shrink(1);
            }
            ItemStack shell = new ItemStack(InitItems.COOKED_BARNACLE_SHELL.get());
            if (!player.inventory.add(shell)) {
                player.drop(shell, false);
            }
        }

        return stack;
    }
}