/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.os;

import java.util.Hashtable;

/**
 * Gives access to the system properties store.  The system properties
 * store contains a list of string key-value pairs.
 *
 * {@hide}
 */
public class SystemProperties
{
    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 91;

    private static String native_get(String key) {return props.containsKey(key) ? props.get(key) : "";}
    private static String native_get(String key, String def) {return props.containsKey(key) ? props.get(key) : def;}
    private static int native_get_int(String key, int def) {return props.containsKey(key) ? Integer.parseInt(props.get(key)) : def;}
    private static long native_get_long(String key, long def) {return props.containsKey(key) ? Long.parseLong(props.get(key)) : def;}
    private static native boolean native_get_boolean(String key, boolean def);
    private static void native_set(String key, String def) {props.put(key, def);}

	private static Hashtable<String, String> props;
	static {
		props = new Hashtable<String, String>();
		props.put("ro.FOREGROUND_APP_ADJ", "0");
		props.put("ro.VISIBLE_APP_ADJ", "1");
		props.put("ro.PERCEPTIBLE_APP_ADJ", "2");
		props.put("ro.HEAVY_WEIGHT_APP_ADJ", "3");
		props.put("ro.SECONDARY_SERVER_ADJ", "4");
		props.put("ro.BACKUP_APP_ADJ", "5");
		props.put("ro.HOME_APP_ADJ", "6");
		props.put("ro.HIDDEN_APP_MIN_ADJ", "7");
		props.put("ro.EMPTY_APP_ADJ", "15");
		props.put("ro.FOREGROUND_APP_MEM", "2048");
		props.put("ro.VISIBLE_APP_MEM", "3072");
		props.put("ro.PERCEPTIBLE_APP_MEM", "4096");
		props.put("ro.HEAVY_WEIGHT_APP_MEM", "4096");
		props.put("ro.SECONDARY_SERVER_MEM", "6144");
		props.put("ro.BACKUP_APP_MEM", "6144");
		props.put("ro.HOME_APP_MEM", "6144");
		props.put("ro.HIDDEN_APP_MEM", "7168");
		props.put("ro.EMPTY_APP_MEM", "8192");
		props.put("net.tcp.buffersize.default", "4096,87380,110208,4096,16384,110208");
		props.put("net.tcp.buffersize.wifi", "4095,87380,110208,4096,16384,110208");
		props.put("net.tcp.buffersize.umts", "4094,87380,110208,4096,16384,110208");
		props.put("net.tcp.buffersize.edge", "4093,26280,35040,4096,16384,35040");
		props.put("net.tcp.buffersize.gprs", "4092,8760,11680,4096,8760,11680");
	}

    /**
     * Get the value for the given key.
     * @return an empty string if the key isn't found
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static String get(String key) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        return native_get(key);
    }

    /**
     * Get the value for the given key.
     * @return if the key isn't found, return def if it isn't null, or an empty string otherwise
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static String get(String key, String def) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        return native_get(key, def);
    }

    /**
     * Get the value for the given key, and return as an integer.
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as an integer, or def if the key isn't found or
     *         cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static int getInt(String key, int def) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        return native_get_int(key, def);
    }

    /**
     * Get the value for the given key, and return as a long.
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a long, or def if the key isn't found or
     *         cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static long getLong(String key, long def) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        return native_get_long(key, def);
    }

    /**
     * Get the value for the given key, returned as a boolean.
     * Values 'n', 'no', '0', 'false' or 'off' are considered false.
     * Values 'y', 'yes', '1', 'true' or 'on' are considered true.
     * (case insensitive).
     * If the key does not exist, or has any other value, then the default
     * result is returned.
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a boolean, or def if the key isn't found or is
     *         not able to be parsed as a boolean.
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static boolean getBoolean(String key, boolean def) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        return native_get_boolean(key, def);
    }

    /**
     * Set the value for the given key.
     * @throws IllegalArgumentException if the key exceeds 32 characters
     * @throws IllegalArgumentException if the value exceeds 92 characters
     */
    public static void set(String key, String val) {
        if (key.length() > PROP_NAME_MAX) {
            throw new IllegalArgumentException("key.length > " + PROP_NAME_MAX);
        }
        if (val != null && val.length() > PROP_VALUE_MAX) {
            throw new IllegalArgumentException("val.length > " +
                PROP_VALUE_MAX);
        }
        native_set(key, val);
    }
}
