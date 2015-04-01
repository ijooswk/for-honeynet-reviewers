package com.fan2fan.util;

import com.fan2fan.model.User;

/**
 *
 * Created by luoruici on 14-4-5.
 */
public class PasswordUtils {

    /**
     * create a user's hashed password
     * @param password
     * @param salt salt
     * @return
     */
    public static String createPassword(String password, String salt) {
        return SecurityUtils.encoderByMd5(SecurityUtils.encoderByMd5(salt)
                .concat(SecurityUtils.encoderBySha512(password)));
    }

    /**
     *
     * @param origin
     * @param encoded
     * @return
     */
    public static boolean authenticatePassword(String origin, String salt, String encoded) {
        return encoded.equals(createPassword(origin, salt));
    }

    /**
     * get user's password salt, using user's createTime
     * @param user
     * @return
     */
    public static String getUserSalt(User user) {
        return user.getCreateTime().toString();
    }
}
