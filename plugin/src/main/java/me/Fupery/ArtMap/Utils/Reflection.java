package me.Fupery.ArtMap.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.Bukkit;

public class Reflection {

    public static final String NMS;

    static {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        NMS = version.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    }

    public Object getField(Object obj, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
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

    public Object getSuperField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(String.format("Field '%s' could not be found in '%s'. Fields found: {%s}",
                    fieldName, obj.getClass().getName(), Arrays.asList(obj.getClass().getDeclaredFields())));
        }
        return field.get(obj);
    }

    public void setField(Object obj, String fieldName, Object value)
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

    public Object invokeMethod(Object obj, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Method '%s' could not be found in '%s'. Methods found: [%s]",
                    methodName, obj.getClass().getName(), Arrays.asList(obj.getClass().getDeclaredMethods())));
        }
        return method.invoke(obj);
    }

    public Object invokeMethod(Object obj, String methodName, Object... parameters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        Class<?>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        try {
            method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException(String.format("Method '%s' could not be found in '%s'. Methods found: [%s]",
                    methodName, obj.getClass().getName(), Arrays.asList(obj.getClass().getDeclaredMethods())));
        }
        return method.invoke(obj, parameters);
    }

    public Object invokeStaticMethod(String className, String methodName, Object... params)
            throws Exception {
        Class<?> obj = Class.forName(NMS + "." + className);

        Class<?>[] paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) paramTypes[i] = params[i].getClass();

        Method method;
        try {
            method = obj.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new Exception(String.format("Method '%s' could not be found in '%s'. Methods found: [%s]",
                    methodName, obj.getName(), Arrays.asList(obj.getMethods())), e);
        }
        return method.invoke(null, params);
    }
}
