package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.Address;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name ="player")
public class PlayerEntity
{
    @Id
    @GeneratedValue(generator = "player_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "player_gen", sequenceName = "player_seq", initialValue = 543390, allocationSize = 1)
    private long id;
    @Email
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "FirstName cannot exceed 0 characters")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Surname is required")
    @Size(max = 20, message = "Surname cannot exceed 20 characters")
    @Column(nullable = false)
    private String surName;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Embedded
    @Valid
    private Address address;

    @Min(value = -1, message = "Daily time limit must be -1 (no limit) or a positive value")
    private int dailyTimeLimit = -1; // Default is "no limit"

    @Min(value = 0, message = "Time used today must be at least 0")
    private int timeUsedToday = 0;

    public PlayerEntity() {
    }

    public PlayerEntity(String email, String password, String firstName, String surName, LocalDate dateOfBirth, Address address) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDailyTimeLimit() {
        return dailyTimeLimit;
    }

    public void setDailyTimeLimit(int dailyTimeLimit) {
        this.dailyTimeLimit = dailyTimeLimit;
    }

    public int getTimeUsedToday() {
        return timeUsedToday;
    }

    public void setTimeUsedToday(int timeUsedToday) {
        this.timeUsedToday = timeUsedToday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
