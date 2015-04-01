package com.fan2fan.web.intercept;

import com.fan2fan.web.session.SessionManager;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Login Required
 * @author huangsz
 */
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionManager.setUp(request.getSession());
        // no interceptor annotations
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod h = (HandlerMethod) handler;
        LoginRequired annotation = h.getMethodAnnotation(LoginRequired.class);
        if (annotation != null && !SessionManager.isLogin()) {
            response.sendRedirect("/account/login/?next=" + request.getRequestURI());
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
