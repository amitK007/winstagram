package com.winstagram.securityservice.config;

import com.winstagram.securityservice.security.JwtAuthTokenFilter;
import com.winstagram.securityservice.security.JwtAuthenticationEntryPoint;
import com.winstagram.securityservice.security.JwtAuthenticationProvider;
import com.winstagram.securityservice.security.JwtSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public JwtAuthTokenFilter authTokenFilter() {
        JwtAuthTokenFilter jwtAuthTokenFilter = new JwtAuthTokenFilter();
        jwtAuthTokenFilter.setAuthenticationManager(authenticationManager());
        jwtAuthTokenFilter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return jwtAuthTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("**/rest/**")
            .authenticated().and()
            .exceptionHandling().authenticationEntryPoint(entryPoint).
            and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    http.headers().cacheControl();
    }
}
