package com.fan2fan.validator.validator;

import com.fan2fan.validator.NullableLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for {@link com.fan2fan.validator.NullableLength}
 * @author huangsz
 */
public class NullableLengthValidator implements ConstraintValidator<NullableLength, String> {

    private int minLength;

    private int maxLength;

    @Override
    public void initialize(NullableLength nullableLength) {
        minLength = nullableLength.min();
        maxLength = nullableLength.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.length() >= minLength && s.length() <= maxLength;
    }
}
