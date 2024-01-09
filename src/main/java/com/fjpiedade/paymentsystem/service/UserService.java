package com.fjpiedade.paymentsystem.service;

import com.fjpiedade.paymentsystem.dto.UserResponse;
import com.fjpiedade.paymentsystem.entity.User;
import com.fjpiedade.paymentsystem.repository.UserRepository;
import com.fjpiedade.paymentsystem.util.RandomString;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public UserResponse createUser(User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("The Email already Exist!");
        }else {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnable(false);
            User saveUser = userRepository.save(user);
            UserResponse userResponse = new UserResponse(saveUser.getId(), saveUser.getName(), saveUser.getEmail(), saveUser.getPassword());

            mailService.sendVerificationEmail(user);
            return userResponse;
        }
    }

    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);

        if(user==null || user.isEnable()){
            return false;
        }else{
            user.setVerificationCode(null);
            user.setEnable(true);
            userRepository.save(user);
            return true;
        }
    }
}
