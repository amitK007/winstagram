package com.winstagram.securityservice.controller;

import com.winstagram.securityservice.model.WinstaUser;
import com.winstagram.securityservice.security.JwtGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    private  JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public String generate(@RequestBody final WinstaUser user) {
        return  jwtGenerator.generate(user);
    }
}
