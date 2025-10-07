package me.Fupery.ArtMap.api.Compatability;

import org.bukkit.map.MapView;

public interface IMapHandler {
    public byte[] getMap(MapView mapView);

    public void setWorldMap(MapView mapView, byte[] colors) throws NoSuchFieldException, IllegalAccessException;
}
