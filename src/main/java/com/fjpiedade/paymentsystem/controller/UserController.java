package com.fjpiedade.paymentsystem.controller;

import com.fjpiedade.paymentsystem.dto.UserRequest;
import com.fjpiedade.paymentsystem.entity.User;
import com.fjpiedade.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest userRequest){
        User user = userRequest.toModel();
        User userSaved = userService.createUser(user);
        System.out.println("user saved: "+userSaved);
        return ResponseEntity.ok().body(userSaved);
    }
}
