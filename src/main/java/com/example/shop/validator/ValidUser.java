package com.example.shop.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserValidator.class)
@Documented
public @interface ValidUser {

    String message() default "Only user allowed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}