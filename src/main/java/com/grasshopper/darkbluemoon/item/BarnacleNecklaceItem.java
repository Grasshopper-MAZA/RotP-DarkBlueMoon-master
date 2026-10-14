package com.grasshopper.darkbluemoon.item;

import com.grasshopper.darkbluemoon.init.InitItems;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class BarnacleNecklaceItem extends ArmorItem implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private Object renderer;

    public BarnacleNecklaceItem(Item.Properties properties) {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, properties);
    }

    public static boolean isEquipped(LivingEntity entity, Item item) {
        if (entity == null) return false;
        return entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() == item;
    }

    public Object getClientRenderer() {
        return renderer;
    }

    public void setClientRenderer(Object renderer) {
        this.renderer = renderer;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("unchecked")
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A defaultModel) {
        if (!(renderer instanceof com.grasshopper.darkbluemoon.client.render.BarnacleNecklaceRenderer)) {
            renderer = new com.grasshopper.darkbluemoon.client.render.BarnacleNecklaceRenderer();
        }
        com.grasshopper.darkbluemoon.client.render.BarnacleNecklaceRenderer armorRenderer =
                (com.grasshopper.darkbluemoon.client.render.BarnacleNecklaceRenderer) renderer;
        armorRenderer.setCurrentItem(entityLiving, itemStack, armorSlot);
        armorRenderer.applyEntityStats(defaultModel);
        return (A) armorRenderer;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "darkbluemoon:textures/models/armor/barnacle_necklace.png";
    }

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}