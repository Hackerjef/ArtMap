package me.Fupery.ArtMap.Compatibility.impl;

import me.Fupery.ArtMap.api.Compatability.IMapHandler;
import org.bukkit.map.MapView;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MapHandler_1_21_4 implements IMapHandler {


    private Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(String.format("Field '%s' could not be found in '%s'. Fields found: {%s}",
                    fieldName, obj.getClass().getName(), Arrays.asList(obj.getClass().getDeclaredFields())));
        }
        return field.get(obj);
    }

    private void setField(Object obj, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(String.format("Field '%s' could not be found in '%s'. Fields found: [%s]",
                    fieldName, obj.getClass().getName(), Arrays.asList(obj.getClass().getDeclaredFields())));
        }
        field.set(obj, value);
    }

    public byte[] getMap(MapView mapView) {
        byte[] colors;

        try {
            Object worldMap = getField(mapView, "worldMap");
            try {
                colors = (byte[]) getField(worldMap, "colors");
            } catch (NoSuchFieldException e) {
                colors = (byte[]) getField(worldMap, "g");
            }
        } catch (NoSuchFieldException | SecurityException
                 | IllegalArgumentException | IllegalAccessException e) {
            colors = null;
        }
        if (colors == null) {
            return new byte[128 * 128];
        }
        return colors;
    }

    public void setWorldMap(MapView mapView, byte[] colors) throws NoSuchFieldException, IllegalAccessException {
        mapView.setCenterX(-999999);
        mapView.setCenterZ(-999999);

        Object worldMap = getField(mapView, "worldMap");
        try {
            setField(worldMap, "colors", colors);
        } catch (NoSuchFieldException e) {
            //Then we must be on 1.17
            setField(worldMap, "g", colors);
        }

        mapView.setScale(MapView.Scale.FARTHEST);
    }
}
