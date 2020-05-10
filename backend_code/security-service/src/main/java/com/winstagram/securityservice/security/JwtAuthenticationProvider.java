package com.winstagram.securityservice.security;

import com.winstagram.securityservice.model.JwtAuthToken;
import com.winstagram.securityservice.model.WinstaUser;
import com.winstagram.securityservice.model.WinstagramUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
            JwtAuthToken jwtAuthToken = (JwtAuthToken) usernamePasswordAuthenticationToken;
            String token = jwtAuthToken.getToken();
        WinstaUser winstaUser = jwtValidator.validate(token);
        if(winstaUser == null) {
            throw  new RuntimeException("JWT Token is incorrect");
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(winstaUser.getRole());
        return new WinstagramUserDetails(winstaUser.getUserName(),
                winstaUser.getId(),token , grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthToken.class.isAssignableFrom(aClass));
    }
}
