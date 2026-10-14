package com.grasshopper.darkbluemoon.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BarnacleShellItem extends Item {

    public BarnacleShellItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}