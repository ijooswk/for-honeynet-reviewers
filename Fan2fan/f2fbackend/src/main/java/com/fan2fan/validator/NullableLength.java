package com.fan2fan.validator;

import com.fan2fan.validator.validator.NullableLengthValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * If null, then don't check. if not null, check the length
 *
 * @author huangsz
 */
@Documented  // documented in java doc
@Target({ElementType.FIELD})  // the applied target
@Retention(RetentionPolicy.RUNTIME)   // life cycle
@Constraint(validatedBy = NullableLengthValidator.class)
public @interface NullableLength {

    /**
     * min length
     * @return
     */
    int min() default 0;

    /**
     * max length
     * @return
     */
    int max() default Integer.MAX_VALUE;
}
