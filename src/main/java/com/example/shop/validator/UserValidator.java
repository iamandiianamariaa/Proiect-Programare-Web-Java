package com.example.shop.validator;

import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<ValidUser, Long> {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        return userService.checkIfUserHasUserRole(userId);
    }
}