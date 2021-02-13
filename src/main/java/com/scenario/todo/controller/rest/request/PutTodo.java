package com.scenario.todo.controller.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PutTodo {

    @Size(min = 3,max = 50, message = "title must be min 3, max 50 character")
    @NotNull(message = "title can not be empty")
    private String title;
}
