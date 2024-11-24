package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import com.example.comeonplayerserviceassignment.utils.ErrorModel;
import com.example.comeonplayerserviceassignment.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;


    public ResponseWrapper<String> registerNewPlayer(PlayerRegistrationRequest playerRegistrationRequest)
    {
        ResponseWrapper<String> response = new ResponseWrapper<>();
        boolean isExist = playerRepository.findByEmail(playerRegistrationRequest.getEmail()).isPresent();
        if (isExist) {
            response.addError(new ErrorModel("10001", "Email Already is use! "));
            return response;
        }
        PlayerEntity player = new PlayerEntity(
                playerRegistrationRequest.getEmail(),
                playerRegistrationRequest.getPassword(),
                playerRegistrationRequest.getFirstName(),
                playerRegistrationRequest.getSurName(),
                playerRegistrationRequest.getDateOfBirth(),
                playerRegistrationRequest.getAddress());

        playerRepository.save(player);
        response.setData("Successfully register the player!");
        return response;
    }
}
