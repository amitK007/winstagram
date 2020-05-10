package com.winstagram.securityservice.security;

import com.winstagram.securityservice.model.WinstaUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
    public String generate(WinstaUser user) {
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        claims.put("id" , String.valueOf(user.getId()));
        claims.put("role" , user.getRole());
        return  Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512 ,"Amit")
                .compact();
    }
}
