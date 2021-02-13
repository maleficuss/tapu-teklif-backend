package com.scenario.todo.controller.rest.request;

import com.scenario.todo.controller.rest.validation.UserAlreadyExist;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostUser {
    @Email(message = "invalid email")
    @Size(min = 5, max = 50, message = "email can be min 5, max 50 characters")
    @UserAlreadyExist(message = "user with this email already exits")
    @NotNull(message = "email can not be null")
    private String email;
    @Size(min = 6, max = 16, message = "password can be min 6, max 16 characters")
    @NotNull(message = "password can not be null")
    private String password;
}
