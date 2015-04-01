package com.fan2fan.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Reflection related utils
 * @author huangsz
 */
public class ReflectionUtils {

    public static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * copy properties from src to target, ignoring null properties
     * @param src
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * copy properties from src to target, but only copy properties that are null in target.
     * @param src
     * @param target
     */
    public static void fillPropertiesWithNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNotNullPropertyNames(target));
    }

    /**
     * return the not null properties, excluding the 'class' property
     * @param src
     * @return
     */
    public static List<String> getObjectNotNullPropertyNames(Object src) {
        List<String> attrs = Lists.newArrayList(getNotNullPropertyNames(src));
        attrs.remove("class");
        return attrs;
    }

    /**
     * get the value of the given attr of an object
     * @param obj
     * @param attr
     * @return
     */
    public static Object getValue(Object obj, String attr) {
        final BeanWrapper src = new BeanWrapperImpl(obj);
        return src.getPropertyValue(attr);
    }

    /**
     * get the properties which are not null
     * @param source
     * @return
     */
    private static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> notNullNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) notNullNames.add(pd.getName());
        }
        String[] result = new String[notNullNames.size()];
        return notNullNames.toArray(result);
    }

    /**
     * get the null properties from the src
     * @param source
     * @return
     */
    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
