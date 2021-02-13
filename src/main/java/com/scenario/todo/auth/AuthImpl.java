package com.scenario.todo.auth;

import com.scenario.todo.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@Getter
@Setter
public class AuthImpl implements Auth{
    private User user;
}
