package com.grasshopper.darkbluemoon.client.render.model;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.item.BarnacleNecklaceItem;

import net.minecraft.util.ResourceLocation;

import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BarnacleNecklaceModel extends AnimatedGeoModel<BarnacleNecklaceItem> {

    @Override
    public ResourceLocation getModelLocation(BarnacleNecklaceItem object) {
        return new ResourceLocation(DarkBlueMoonMain.MOD_ID, "geo/barnacle_necklace.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BarnacleNecklaceItem object) {
        return new ResourceLocation(DarkBlueMoonMain.MOD_ID, "textures/models/armor/barnacle_necklace.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BarnacleNecklaceItem animatable) {
        return new ResourceLocation(DarkBlueMoonMain.MOD_ID, "animations/barnacle_necklace.animation.json");
    }
}