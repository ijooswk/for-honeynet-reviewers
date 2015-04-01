package com.fan2fan.web.views.account.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CodeExistValidator.class)
public @interface CodeExist {

    boolean exist() default false;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
