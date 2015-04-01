package com.fan2fan.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.UUID;

/**
 * Security Related Utils, like hash, encription and so on
 * @autho huangsz
 */
public class SecurityUtils
{
    /**
     *
     * @param str
     * @return md5 hashed str
     */
    public static String encoderByMd5(String str) {
        return Hashing.md5().newHasher()
                .putString(str, Charsets.UTF_8)
                .hash().toString();
    }

    /**
     *
     * @param str
     * @return
     */
    public static String encoderBySha512(String str) {
        return Hashing.sha512().newHasher()
                .putString(str, Charsets.UTF_8)
                .hash().toString();
    }

    /**
     * generate token
     * @return
     */
    public static String genToken() {
        return UUID.randomUUID().toString();
    }
}
