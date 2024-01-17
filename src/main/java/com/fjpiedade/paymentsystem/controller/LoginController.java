package com.fjpiedade.paymentsystem.controller;

import com.fjpiedade.paymentsystem.dto.AuthenticationRequest;
import com.fjpiedade.paymentsystem.dto.AuthenticationResponse;
import com.fjpiedade.paymentsystem.entity.User;
import com.fjpiedade.paymentsystem.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest){
        var userNamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
        );

        var auth = authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @GetMapping("/test")
    public String test(){
        return "You are in on the Application";
    }
}
