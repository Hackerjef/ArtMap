package me.Fupery.ArtMap.Compatibility.impl;

import me.Fupery.ArtMap.api.Compatability.IMapHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.map.MapView;

public class MapHandler_1_21_6 implements IMapHandler {
    @Override
    public byte[] getMap(MapView mapView) {
        CraftMapView craftMapView = (CraftMapView) mapView;
        if (craftMapView.getWorld() == null) {
            return new byte[128 * 128];
        }
        ServerLevel w = ((CraftWorld)craftMapView.getWorld()).getHandle();
        MapItemSavedData savedData = w.getMapData(new MapId(craftMapView.getId()));

        if (savedData == null) {
            return new byte[128 * 128];
        }

        return savedData.colors;
    }

    @Override
    public void setWorldMap(MapView mapView, byte[] colors) throws NoSuchFieldException, IllegalAccessException {
        mapView.setCenterX(-999999);
        mapView.setCenterZ(-999999);

        CraftMapView craftMapView = (CraftMapView) mapView;
        if (craftMapView.getWorld() == null) {
            System.out.println("Craft Map data is null!");
            return;
        }
        MapId mapId = new MapId(craftMapView.getId());

        ServerLevel w = ((CraftWorld)craftMapView.getWorld()).getHandle();
        MapItemSavedData savedData = w.getMapData(mapId);

        if (savedData == null) {
            System.out.println("Map data is null!");
            return;
        }

        savedData.centerX = -999999;
        savedData.centerZ = -999999;
        savedData.colors = colors;

        w.setMapData(mapId, savedData);
    }
}
