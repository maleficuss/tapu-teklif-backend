package com.scenario.todo.controller.rest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserAlreadyExistValidator.class)
@Documented
public @interface UserAlreadyExist {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "";
}