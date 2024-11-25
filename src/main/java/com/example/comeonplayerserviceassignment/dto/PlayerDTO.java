package com.example.comeonplayerserviceassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO
{
    private long id;
    private String email;
    private String firstName;
    private String surName;
    private LocalDate dateOfBirth;
    private AddressDTO address;
    private long dailyTimeLimit = -1;
    private List<SessionDTO> sessions;

    public PlayerDTO(long id, String email, String firstName, String surName, LocalDate dateOfBirth, AddressDTO address, long dailyTimeLimit) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.dailyTimeLimit = dailyTimeLimit;
    }

}
