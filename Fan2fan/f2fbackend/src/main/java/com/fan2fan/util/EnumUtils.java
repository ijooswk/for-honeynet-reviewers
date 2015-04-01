package com.fan2fan.util;

/**
 * @author huangsz
 */
public class EnumUtils {

    /**
     * obj is an enum type or not
     * @param obj
     * @param <T>
     * @return
     */
    public static<T> boolean isEnum(T obj) {
        return obj.getClass().isEnum();
    }

    /**
     * get an enumType by this enumType's index;
     * throws RuntimeException if T is not an enum type or index is out of rage
     * @param index
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getEnumByIndex(int index, Class<T> clazz) {
        return clazz.getEnumConstants()[index];
    }

    /**
     * get the enumType's index, namely the position of this type in its enum definitions.
     * throws RuntimeException when T is not an enum type or index is out of rage.
     * @param obj
     * @param <T>
     * @return
     */
    public static<T> int getIndexByEnum(T obj) {
        Object[] enums = obj.getClass().getEnumConstants();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].equals(obj)) {
                return i;
            }
        }
        throw new RuntimeException(String.format("%s is not in enum", obj.toString()));
    }
}
