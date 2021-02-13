package com.scenario.todo.controller.rest.validation;

import com.scenario.todo.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserAlreadyExistValidator implements ConstraintValidator<UserAlreadyExist,String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByEmail(email);
    }
}
