package com.scenario.todo.rest_response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
public class RestResponse<T> {
    private int status;
    private T data;
    private String message;
    @Builder.Default
    private Map<String,String> errors = new LinkedHashMap<>();
    @Builder.Default
    private Instant timestamp = Instant.now();
}
