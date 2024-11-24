package com.example.comeonplayerserviceassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO
{
    private String id;
    private PlayerDTO player;
    private LocalDateTime startTime;
    private LocalDate sessionDate;
    private long dailyTimeUsedByPlayerInMinutes;
    private boolean loggedIn;
    private boolean active;
}
