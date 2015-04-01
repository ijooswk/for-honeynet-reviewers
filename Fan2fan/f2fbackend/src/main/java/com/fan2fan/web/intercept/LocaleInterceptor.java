package com.fan2fan.web.intercept;

import com.fan2fan.web.session.SessionManager;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor of locale change and locale setting
 * @author huangsz
 */
public class LocaleInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        RequestContext ctx = new RequestContext(request);
        SessionManager.setLocale(ctx.getLocale());
        return super.preHandle(request, response, handler);
    }
}
