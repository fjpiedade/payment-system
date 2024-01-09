package com.fjpiedade.paymentsystem.service;

import com.fjpiedade.paymentsystem.entity.User;
import com.fjpiedade.paymentsystem.repository.UserRepository;
import com.fjpiedade.paymentsystem.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("The Email already Exist!");
        }else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            String randomCode = RandomString.generateRandomString(23);
            user.setVerificationCode(randomCode);
            user.setEnable(false);
            User saveUser = userRepository.save(user);
            return saveUser;
        }
    }
}
