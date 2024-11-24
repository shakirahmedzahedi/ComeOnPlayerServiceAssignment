package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import com.example.comeonplayerserviceassignment.utils.JsonUtils;
import com.example.comeonplayerserviceassignment.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/player")
public class PlayerController
{
    @Autowired
    PlayerService playerService;
    @Autowired
    JsonUtils jsonUtils;

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> registerNewPlayer(@RequestBody PlayerRegistrationRequest playerRegistrationRequest)
    {
        ResponseWrapper<String> response = playerService.registerNewPlayer(playerRegistrationRequest);

        return jsonUtils.responseAsJson(response);
    }

}
