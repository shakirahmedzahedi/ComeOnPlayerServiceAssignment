package com.example.comeonplayerserviceassignment.session;

import com.example.comeonplayerserviceassignment.palyer.PlayerEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="session")
public class SessionEntity {

    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false, unique = true)
    private PlayerEntity player;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Long dailyTimeLimitInMinutes;

    @Column(nullable = false)
    private Long dailyTimeUsedByPlayerInMinutes;

    @Column(nullable = false)
    private boolean loggedIn;
    @Column(nullable = false)
    private boolean active;

    public SessionEntity(PlayerEntity player, LocalDateTime startTime, long dailyTimeLimitInMinutes) {
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.startTime = startTime;
        this.dailyTimeLimitInMinutes = dailyTimeLimitInMinutes;
        this.dailyTimeUsedByPlayerInMinutes = Long.valueOf(0);
        this.loggedIn = true;
        this.active = true;
    }

    public SessionEntity() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getDailyTimeLimitInMinutes() {
        return dailyTimeLimitInMinutes;
    }

    public void setDailyTimeLimitInMinutes(Long dailyTimeLimitInMinutes) {
        this.dailyTimeLimitInMinutes = dailyTimeLimitInMinutes;
    }

    public Long getDailyTimeUsedByPlayerInMinutes() {
        return dailyTimeUsedByPlayerInMinutes;
    }

    public void setDailyTimeUsedByPlayerInMinutes(Long dailyTimeUsedByPlayerInMinutes) {
        this.dailyTimeUsedByPlayerInMinutes  += java.time.Duration.between(startTime, LocalDateTime.now()).toMinutes();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public boolean hasExceededTimeLimit() {
        if (dailyTimeLimitInMinutes == -1)
        {
            return false;
        }
        return this.dailyTimeUsedByPlayerInMinutes >= this.dailyTimeLimitInMinutes;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id='" + id + '\'' +
                ", player=" + player +
                ", startTime=" + startTime +
                ", dailyTimeLimitInMinutes=" + dailyTimeLimitInMinutes +
                ", dailyTimeUsedByPlayerInMinutes=" + dailyTimeUsedByPlayerInMinutes +
                ", loggedIn=" + loggedIn +
                ", active=" + active +
                '}';
    }
}
