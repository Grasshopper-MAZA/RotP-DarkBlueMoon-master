package com.grasshopper.darkbluemoon.client;

import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.client.marker.BarnacleTrapMarker;
import com.grasshopper.darkbluemoon.client.render.BarnaclePullRenderer;
import com.grasshopper.darkbluemoon.client.render.BarnacleRenderer;
import com.grasshopper.darkbluemoon.client.render.DarkBlueMoonMiniTornadoRenderer;
import com.grasshopper.darkbluemoon.client.render.DarkBlueMoonRenderer;
import com.grasshopper.darkbluemoon.client.render.DarkBlueMoonWhirlpoolRenderer;
import com.grasshopper.darkbluemoon.client.render.ScaleShotRenderer;
import com.grasshopper.darkbluemoon.init.InitEntities;
import com.grasshopper.darkbluemoon.init.InitStands;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = DarkBlueMoonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_DARK_BLUE_MOON.getEntityType(),
                DarkBlueMoonRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.SCALE_SHOT.get(),
                ScaleShotRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.BARNACLE.get(),
                BarnacleRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.BARNACLE_PULL.get(),
                BarnaclePullRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.WHIRLPOOL.get(),
                DarkBlueMoonWhirlpoolRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.MINI_WATER_TORNADO.get(),
                DarkBlueMoonMiniTornadoRenderer::new);

        Minecraft mc = event.getMinecraftSupplier().get();
        com.github.standobyte.jojo.client.ui.marker.MarkerRenderer.Handler.addRenderer(
                new BarnacleTrapMarker(mc));
    }
}