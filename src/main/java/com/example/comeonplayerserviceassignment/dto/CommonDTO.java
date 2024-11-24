package com.example.comeonplayerserviceassignment.dto;

import com.example.comeonplayerserviceassignment.palyer.PlayerEntity;
import com.example.comeonplayerserviceassignment.session.SessionEntity;

import java.util.stream.Collectors;

public class CommonDTO
{
    public PlayerDTO toPlayerDTO(PlayerEntity playerEntity)
    {
        AddressDTO addressDTO = new AddressDTO(
                playerEntity.getAddress().getAddressLine1(),
                playerEntity.getAddress().getAddressLine2(),
                playerEntity.getAddress().getPostCode(),
                playerEntity.getAddress().getCity());

        return new PlayerDTO(
                playerEntity.getId(),
                playerEntity.getEmail(),
                playerEntity.getFirstName(),
                playerEntity.getSurName(),
                playerEntity.getDateOfBirth(),
                addressDTO,
                playerEntity.getDailyTimeLimit(),
                playerEntity.getSessionEntityList().stream()
                        .map(this::toSessionDTO)
                        .collect(Collectors.toList()));
    }

    public SessionDTO toSessionDTO(SessionEntity sessionEntity)
    {
        AddressDTO addressDTO = new AddressDTO(
                sessionEntity.getPlayer().getAddress().getAddressLine1(),
                sessionEntity.getPlayer().getAddress().getAddressLine2(),
                sessionEntity.getPlayer().getAddress().getPostCode(),
                sessionEntity.getPlayer().getAddress().getCity());

        PlayerDTO playerDTO = new PlayerDTO(
                sessionEntity.getPlayer().getId(),
                sessionEntity.getPlayer().getEmail(),
                sessionEntity.getPlayer().getFirstName(),
                sessionEntity.getPlayer().getSurName(),
                sessionEntity.getPlayer().getDateOfBirth(),
                addressDTO,
                sessionEntity.getPlayer().getDailyTimeLimit());

        return new SessionDTO(
                sessionEntity.getId(),
                playerDTO,
                sessionEntity.getStartTime(),
                sessionEntity.getDailyTimeLimitInMinutes(),
                sessionEntity.getDailyTimeUsedByPlayerInMinutes(),
                sessionEntity.isLoggedIn(),
                sessionEntity.isActive());



    }

}
