package com.grasshopper.darkbluemoon.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.pose.IModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.anim.barrage.BarrageSwingsHolder;
import com.github.standobyte.jojo.client.render.entity.pose.anim.barrage.StandArmBarrageSwing;
import com.github.standobyte.jojo.client.render.entity.pose.anim.barrage.StandTwoHandedBarrageAnimation;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.util.HandSide;

public class DarkBlueMoonBarrageAnimation extends StandTwoHandedBarrageAnimation<DarkBlueMoonEntity> {

    public DarkBlueMoonBarrageAnimation(StandEntityModel<DarkBlueMoonEntity> model,
                                        IModelPose<DarkBlueMoonEntity> loop,
                                        IModelPose<DarkBlueMoonEntity> recovery) {
        super(model, loop, recovery);
    }

    @Override
    public BarrageSwingsHolder<DarkBlueMoonEntity, StandEntityModel<DarkBlueMoonEntity>> getBarrageSwingsHolder(DarkBlueMoonEntity entity) {
        return (BarrageSwingsHolder<DarkBlueMoonEntity, StandEntityModel<DarkBlueMoonEntity>>) entity.getBarrageSwingsHolder();
    }

    @Override
    protected float swingsToAdd(DarkBlueMoonEntity entity, float loop, float lastLoop) {
        return standEntitySwings(entity, loop, lastLoop, getLoopLen());
    }

    @Override
    protected double maxSwingOffset(DarkBlueMoonEntity entity) {
        return maxStandSwingOffset(entity);
    }

    @Override
    protected void addSwing(DarkBlueMoonEntity entity,
                            BarrageSwingsHolder<DarkBlueMoonEntity, StandEntityModel<DarkBlueMoonEntity>> swings,
                            HandSide side, float f, double maxOffset) {
        swings.addSwing(new StandArmBarrageSwing<>(this, f, getLoopLen(), side, maxOffset));
    }

    @Override
    public void animateSwing(DarkBlueMoonEntity entity, StandEntityModel<DarkBlueMoonEntity> model,
                             float loopCompletion, HandSide side,
                             float yRotOffsetRad, float xRotRad, float zRotOffsetRad) {
        HandSide flipped = (side == HandSide.LEFT) ? HandSide.RIGHT : HandSide.LEFT;
        super.animateSwing(entity, model, loopCompletion, flipped,
                -yRotOffsetRad, xRotRad, zRotOffsetRad);
    }

    @Override
    public void beforeSwingAfterimageRender(MatrixStack matrixStack,
                                            StandEntityModel<DarkBlueMoonEntity> model,
                                            float loopCompletion, HandSide side) {
        model.applyXRotation();
    }
}