package com.grasshopper.darkbluemoon.event;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.init.InitItems;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
public class BarnacleSplitHandler {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide()) {
            return;
        }

        ItemStack crafted = event.getCrafting();
        if (crafted.getItem() != InitItems.BARNACLE_MEAT.get()) {
            return;
        }

        IInventory inv = event.getInventory();
        if (!(inv instanceof CraftingInventory)) {
            return;
        }
        CraftingInventory craftInv = (CraftingInventory) inv;

        boolean hasBarnacle = false;
        boolean hasShears = false;

        for (int i = 0; i < craftInv.getContainerSize(); i++) {
            ItemStack stack = craftInv.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() == InitItems.BARNACLE.get()) {
                hasBarnacle = true;
            } else if (stack.getItem() instanceof ShearsItem || stack.getItem() == Items.SHEARS) {
                hasShears = true;
            }
        }

        if (!hasBarnacle || !hasShears) {
            return;
        }


        ItemStack shell = new ItemStack(InitItems.BARNACLE_SHELL.get());
        if (!player.inventory.add(shell)) {
            player.drop(shell, false);
        }


        for (int i = 0; i < craftInv.getContainerSize(); i++) {
            ItemStack stack = craftInv.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof ShearsItem || stack.getItem() == Items.SHEARS) {
                if (!player.abilities.instabuild) {
                    ItemStack returned = new ItemStack(Items.SHEARS);
                    returned.setDamageValue(stack.getDamageValue() + 2);
                    if (returned.getDamageValue() < returned.getMaxDamage()) {
                        if (!player.inventory.add(returned)) {
                            player.drop(returned, false);
                        }
                    }
                }
            }
        }

        player.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
    }
}