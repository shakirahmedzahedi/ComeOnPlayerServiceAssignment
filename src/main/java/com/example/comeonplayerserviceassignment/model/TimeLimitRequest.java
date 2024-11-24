package com.example.comeonplayerserviceassignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TimeLimitRequest
{
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Time Limit is required")
    @Size(min = 1, max = 23, message = "Time Limit must be between 1 and 23")
    private double timeLimitInHours;
}
