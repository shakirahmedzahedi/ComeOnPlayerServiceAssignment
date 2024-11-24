package com.example.comeonplayerserviceassignment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PlayerRegistrationRequest
{
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "FirstName is required")
    @Size(max = 20, message = "FirstName cannot exceed 20 characters")
    private String firstName;

    @NotBlank(message = "Surname is required")
    @Size(max = 20, message = "Surname cannot exceed 20 characters")
    private String surName;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Valid
    private Address address;


}
