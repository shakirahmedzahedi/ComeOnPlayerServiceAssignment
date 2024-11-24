package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.Address;
import com.example.comeonplayerserviceassignment.session.SessionEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="player")
public class PlayerEntity
{
    @Id
    @GeneratedValue(generator = "player_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "player_gen", sequenceName = "player_seq", initialValue = 543390, allocationSize = 1)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String surName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Embedded
    @Valid
    private Address address;

    private int dailyTimeLimit = -1; // Default is "no limit"

    private int timeUsedToday = 0;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<SessionEntity> sessionEntityList = new ArrayList<>();

    public PlayerEntity() {
    }

    public PlayerEntity(String email, String password, String firstName, String surName, LocalDate dateOfBirth, Address address) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.sessionEntityList = new ArrayList<>();
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

    public List<SessionEntity> getSessionEntityList() {
        return sessionEntityList;
    }

    public void setSessionEntityList(List<SessionEntity> sessionEntityList) {
        this.sessionEntityList = sessionEntityList;
    }
    public void addSession(SessionEntity session)
    {
        this.sessionEntityList.add(session);
    }

    @Override
    public String toString() {
        return "PlayerEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", dailyTimeLimit=" + dailyTimeLimit +
                ", timeUsedToday=" + timeUsedToday +
                ", sessionEntityList=" + sessionEntityList +
                '}';
    }
}
