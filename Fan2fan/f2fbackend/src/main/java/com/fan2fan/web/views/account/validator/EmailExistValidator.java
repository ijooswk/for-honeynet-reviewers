package com.fan2fan.web.views.account.validator;

import com.fan2fan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {

    @Autowired
    private UserService userService;

    private boolean exists;

    @Override
    public void initialize(EmailExist emailExist) {
        this.exists = emailExist.exist();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(exists ^ userService.emailRegistered(s));
    }
}
