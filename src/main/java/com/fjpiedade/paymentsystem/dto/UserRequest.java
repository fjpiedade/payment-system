package com.fjpiedade.paymentsystem.dto;

import com.fjpiedade.paymentsystem.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "Name should not be null/empty")
        @NotBlank(message = "Name should not be null/empty")
        @Size(min = 3, message = "Name should more than 2 letter")
        String name,
        @NotNull(message = "Email should not be null/empty")
        @NotBlank(message = "Email should not be null/empty")
        @Email(message = "Email not valid")
        String email,
        @NotNull(message = "Password should not be null/empty")
        @NotBlank(message = "Password should not be null/empty")
        @Size(min = 8, message = "Password should more than 8 letter")
        String password) {

    public User toModel() {
        return new User(name, email, password);
    }
}
