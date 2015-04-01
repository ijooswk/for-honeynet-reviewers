package com.fan2fan.web.intercept;

import com.fan2fan.model.User;

import java.lang.annotation.*;

/**
 * Authority Annotation, works for AuthorityInterceptor
 *
 * @author huangsz
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserTypeAuth {
    User.USER_TYPE[] value() default User.USER_TYPE.END_USER;
}
