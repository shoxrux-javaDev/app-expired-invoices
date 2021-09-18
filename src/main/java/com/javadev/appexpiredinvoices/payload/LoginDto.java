package com.javadev.appexpiredinvoices.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email
    @NotBlank(message = "please enter your email")
    private String email;

    @NotBlank(message = "please enter your password")
    private String password;
}
