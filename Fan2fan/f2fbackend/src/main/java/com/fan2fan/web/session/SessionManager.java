package com.fan2fan.web.session;

import com.fan2fan.model.User;

import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * @author huangsz
 */
public class SessionManager {

    private static final String USER = "user";

    private static final String USER_ID = "userId";

    private static final String USERNAME = "userName";

    private static final String LOCALE = "locale";

    private static ThreadLocal<HttpSession> threadSession = new ThreadLocal<>();

    public static void setUp(HttpSession session) {
        threadSession.set(session);
    }

    public static void setUser(User user) {
        setAttribute(USER, user);
        setAttribute(USER_ID, user.getId());
        setAttribute(USERNAME, user.getUserName());
    }

    public static void setLocale(Locale locale) {
        setAttribute(LOCALE, locale);
    }

    public static void removeUser() {
        threadSession.get().removeAttribute(USER);
        threadSession.get().removeAttribute(USER_ID);
        threadSession.get().removeAttribute(USERNAME);
    }

    public static void setAttribute(String key, Object obj) {
        threadSession.get().setAttribute(key, obj);
    }

    public static <T> T getAttribute(String key, Class<T> clazz) {
        Object obj = threadSession.get().getAttribute(key);
        return obj == null ? null : clazz.cast(obj);
    }

    public static void removeAttribute(String key) {
        threadSession.get().removeAttribute(key);
    }

    public static User getUser() {
        return getAttribute(USER, User.class);
    }

    public static Long getUserId() {
        return getAttribute(USER_ID, Long.class);
    }

    public static String getUserName() {
        return getAttribute(USERNAME, String.class);
    }

    public static String getLocale() { return getAttribute(LOCALE, Locale.class).toString(); }

    public static boolean isLogin() { return getUser() != null; }
}
