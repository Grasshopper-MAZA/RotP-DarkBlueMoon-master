package com.grasshopper.darkbluemoon.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CookedBarnacleShellItem extends Item {

    public CookedBarnacleShellItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 400;
    }
}