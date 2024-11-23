package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController
{
    @Autowired
    PlayerService playerService;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> registerNewPlayer(@RequestBody PlayerRegistrationRequest playerRegistrationRequest)
    {
        String response = playerService.registerNewPlayer(playerRegistrationRequest);

        return ResponseEntity.ok(response);
    }

}
