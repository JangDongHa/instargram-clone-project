package com.clone.instargram.config.jwt;


// 시큐리티가 filter 를 가지고 있는데 그 filter 중에 BasicAuthenticationFilter 라는 것이 있음
// 무조건 해당 filter 를 타긴 하는데 이 filter 가 하는 일은 기본적으로 토큰이 있는지(현재 클래스 기준) 확인하고 그에 따른 처리를 진행하는 것

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.config.jwt.token.properties.AccessTokenProperties;
import com.clone.instargram.config.jwt.token.properties.CommonTokenProperties;
import com.clone.instargram.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        String jwtHeader = request.getHeader(AccessTokenProperties.HEADER_STRING);

        // JWT Token 을 검증하여 정상적인 사용자인지 확인해봐야함
        if (jwtHeader == null || !jwtHeader.startsWith(CommonTokenProperties.TOKEN_PREFIX)){ // 헤더가 없거나 Bearer 이 아닌 경우
            chain.doFilter(request, response);
            return;
        }

        RequestToken requestToken = new RequestToken(request);
        //String usernameInAccessToken = requestToken.getTokenElement(requestToken.getAccessToken(), "username").orElseThrow();

        chain.doFilter(request, response);


    }
}