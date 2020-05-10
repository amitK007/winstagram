package com.winstagram.securityservice.security;

import com.winstagram.securityservice.model.JwtAuthToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthTokenFilter extends AbstractAuthenticationProcessingFilter {
    private  AuthenticationManager authenticationManger;
    public  JwtAuthTokenFilter() {
        super("/rest/**");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String header = httpServletRequest.getHeader("Authorisation");
        if(header == null || !header.startsWith("Token ")) {
            throw  new RuntimeException("JWT Token is missing.");
        }
        String authenticationToken = header.substring(6);
        JwtAuthToken jwtAuthToken =new JwtAuthToken(authenticationToken);

        return getAuthenticationManager().authenticate(jwtAuthToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request,response);
    }
}
