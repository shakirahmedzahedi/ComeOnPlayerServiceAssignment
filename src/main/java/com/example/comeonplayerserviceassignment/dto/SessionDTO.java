package com.example.comeonplayerserviceassignment.dto;

import com.example.comeonplayerserviceassignment.palyer.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO
{
    private String id;
    private PlayerDTO player;
    private LocalDateTime startTime;
    private Long dailyTimeLimitInMinutes;
    private Long dailyTimeUsedByPlayerInMinutes;
    private boolean loggedIn;
    private boolean active;
}
