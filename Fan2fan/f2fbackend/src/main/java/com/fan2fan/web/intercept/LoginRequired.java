package com.fan2fan.web.intercept;

import java.lang.annotation.*;

/**
 * Login Required Annotation
 *
 * @author huangsz
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {

}
