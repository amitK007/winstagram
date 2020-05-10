package com.winstagram.securityservice.security;

import com.winstagram.securityservice.model.WinstaUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private String secret = "Amit";
    public WinstaUser validate(String token) {
        WinstaUser winstaUserDetails = null;
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            winstaUserDetails = new WinstaUser();
            winstaUserDetails.setUserName(body.getSubject());
            winstaUserDetails.setId(Long.parseLong((String) body.get("userName")));
            winstaUserDetails.setRole((String) body.get("role"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return winstaUserDetails;
    }
}
