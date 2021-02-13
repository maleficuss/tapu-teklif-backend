package com.scenario.todo.controller.rest.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostToken {
    private String email;
    private String password;
}
