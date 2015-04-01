package com.fan2fan.web.views.account.validator;

import com.fan2fan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodeExistValidator implements ConstraintValidator<CodeExist, String> {

    private boolean exists;

    @Autowired
    private UserService userService;

    @Override
    public void initialize(CodeExist codeExist) {
        this.exists = codeExist.exist();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
        return exists ^ userService.isInvitationCodeValid(code);
    }
}
