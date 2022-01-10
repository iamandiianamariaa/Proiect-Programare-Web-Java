package com.example.shop.validator;

import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AdminValidator implements ConstraintValidator<ValidAdmin, Long> {

    private final UserService userService;

    @Autowired
    public AdminValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(Long adminId, ConstraintValidatorContext constraintValidatorContext) {
        return userService.checkIfUserHasAdminRole(adminId);
    }
}