package com.fan2fan.web.intercept;

import com.fan2fan.model.User;
import com.fan2fan.web.session.SessionManager;
import com.google.common.collect.Lists;
import org.apache.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * check the authority, like login or not
 * <p>
 * Created by huangsz on 2014/5/19.
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        // no interceptor annotations
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        HandlerMethod h = (HandlerMethod) handler;
        UserTypeAuth annotation = h.getMethodAnnotation(UserTypeAuth.class);
        if (annotation != null && (!SessionManager.isLogin()
                || !hasAuthority(SessionManager.getUser(), annotation.value()))) {
            response.sendError(HttpStatus.SC_FORBIDDEN);
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    private boolean hasAuthority(User user, User.USER_TYPE[] requires) {
        return Lists.newArrayList(requires)
                .stream()
                .anyMatch(e -> user.getType().ordinal() >= e.ordinal());
    }
}
