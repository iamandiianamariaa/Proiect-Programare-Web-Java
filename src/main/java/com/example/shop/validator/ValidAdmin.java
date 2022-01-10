package com.example.shop.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdminValidator.class)
@Documented
public @interface ValidAdmin {

    String message() default "Only admin allowed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}