package com.scenario.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scenario.todo.auth.Auth;
import com.scenario.todo.entity.User;
import com.scenario.todo.rest_response.RestResponse;
import com.scenario.todo.service.UserService;
import com.scenario.todo.token_manager.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
@Order(1)
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final ObjectMapper mapper;
    private final UserService userService;
    private final static List<String> dontFilterPaths = new ArrayList<>();
    private final Auth auth;

    static {
        dontFilterPaths.add("/api/token");
        dontFilterPaths.add("/api/users");
        dontFilterPaths.add("/test");
    }


    private String getTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header == null || header.length() < 7) {
            return "";
        }
        return header.substring(7);
    }

    private void addErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(errorMessage)
                .build();

        response.setStatus(restResponse.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(mapper.writeValueAsString(restResponse));
        return;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return dontFilterPaths.stream().anyMatch(path -> path.equals(request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHeader(request);

        try {
            Jws<Claims> claims = tokenManager.getParser().parseClaimsJws(token);
            String username = claims.getBody().getSubject();
            if (!userService.existsByEmail(username)) {
                addErrorResponse(response,"InvalidToken");
                return;
            }

            User user = userService.findByEmail(username);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(), Collections.emptyList());
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            auth.setUser(user);


        } catch (ExpiredJwtException e) {

            addErrorResponse(response,"TokenExpired");
            return;

        } catch (Exception e) {
            addErrorResponse(response,"InvalidToken");
            return;
        }

        filterChain.doFilter(request,response);
    }


}
