package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;


    public String registerNewPlayer(PlayerRegistrationRequest playerRegistrationRequest)
    {
        boolean isExist = playerRepository.findByEmail(playerRegistrationRequest.getEmail()).isPresent();
        if (isExist) {
            return "Email is already in use!";
        }
        PlayerEntity player = new PlayerEntity(
                playerRegistrationRequest.getEmail(),
                playerRegistrationRequest.getPassword(),
                playerRegistrationRequest.getFirstName(),
                playerRegistrationRequest.getSurName(),
                playerRegistrationRequest.getDateOfBirth(),
                playerRegistrationRequest.getAddress());

        playerRepository.save(player);
        return "Successfully register the player!";
    }
}
