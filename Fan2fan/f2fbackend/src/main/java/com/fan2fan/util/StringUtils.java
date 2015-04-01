package com.fan2fan.util;

import com.google.common.base.CharMatcher;

/**
 * @author huangsz
 */
public class StringUtils {

    public static String getWhiteSpaces() {
        return "\t\n \f\r";
    }

    public static String trimWith(String original, String trimoff) {
        CharMatcher matcher = CharMatcher.anyOf(trimoff);
        return matcher.trimFrom(original);
    }
}
