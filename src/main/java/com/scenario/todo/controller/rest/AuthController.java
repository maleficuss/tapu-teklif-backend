package com.scenario.todo.controller.rest;

import com.scenario.todo.controller.rest.request.PostUser;
import com.scenario.todo.entity.User;
import com.scenario.todo.mapper.UserMapper;
import com.scenario.todo.rest_response.RestResponse;
import com.scenario.todo.service.UserService;
import com.scenario.todo.token_manager.JwtToken;
import com.scenario.todo.token_manager.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    @PostMapping(value = "users", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Void> save(@Valid PostUser postUser){
        User user = userMapper.map(postUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("auth").replaceQuery("").build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("token")
    public ResponseEntity<RestResponse<JwtToken>> token(Authentication authentication){

        RestResponse<JwtToken> restResponse = RestResponse.<JwtToken>builder()
                .status(HttpStatus.CREATED.value())
                .data(tokenManager.createToken(authentication.getName()))
                .build();

        return ResponseEntity.created(null).body(restResponse);
    }


}
