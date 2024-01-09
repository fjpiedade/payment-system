package com.fjpiedade.paymentsystem.controller;

import com.fjpiedade.paymentsystem.dto.UserRequest;
import com.fjpiedade.paymentsystem.dto.UserResponse;
import com.fjpiedade.paymentsystem.entity.User;
import com.fjpiedade.paymentsystem.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws MessagingException, UnsupportedEncodingException {
        User user = userRequest.toModel();
        UserResponse userSaved = userService.createUser(user);
        System.out.println("user saved: "+userSaved);
        return ResponseEntity.ok().body(userSaved);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return "verify_success";
        }else {
            return "verify_fail";
        }
    }
}
