package com.grasshopper.darkbluemoon.client.marker;

import java.util.List;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.grasshopper.darkbluemoon.DarkBlueMoonMain;
import com.grasshopper.darkbluemoon.entity.DarkBlueMoonEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class BarnacleTrapMarker extends MarkerRenderer {

    public BarnacleTrapMarker(Minecraft mc) {
        super(new ResourceLocation(DarkBlueMoonMain.MOD_ID,
                "textures/action/barnacle_trap_marker.png"), mc);
    }

    @Override
    protected boolean shouldRender() {
        IStandManifestation stand = IStandPower.getPlayerStandPower(mc.player).getStandManifestation();
        if (stand instanceof DarkBlueMoonEntity) {
            return ((DarkBlueMoonEntity) stand).hasBarnacleTrap();
        }
        return false;
    }

    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        IStandManifestation stand = IStandPower.getPlayerStandPower(mc.player).getStandManifestation();
        if (stand instanceof DarkBlueMoonEntity) {
            DarkBlueMoonEntity dbm = (DarkBlueMoonEntity) stand;
            if (dbm.hasBarnacleTrap()) {
                BlockPos pos = dbm.getBarnacleTrapPos();
                Vector3d markerPos = new Vector3d(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
                list.add(new Marker(markerPos, true));
            }
        }
    }

    public static class Marker extends MarkerRenderer.MarkerInstance {
        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }
    }
}