package com.scenario.todo.projection;

import java.util.Date;

public interface TodoWithoutUser {
    int getId();
    String getTitle();
    Date getDate();
}
