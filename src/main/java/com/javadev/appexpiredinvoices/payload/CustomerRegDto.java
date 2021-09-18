package com.javadev.appexpiredinvoices.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegDto {
    @NotBlank(message = "Username should not be empty")
    private String username;

    @NotBlank(message = "Password should not be empty")
    private String password;

    @NotBlank(message = "PrePassword should not be empty")
    private String prePassword;

    @NotBlank(message = "Country should not be empty")
    private String country;

    @NotBlank(message = "Email should not be empty")
    @Email
    private String email;

    @NotBlank(message = "Address should not be empty")
    private String address;

    @NotBlank(message = "{Phone number should not be empty")
    private String phone;
}
