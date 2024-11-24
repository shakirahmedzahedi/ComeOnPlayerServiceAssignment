package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.dto.CommonDTO;
import com.example.comeonplayerserviceassignment.dto.PlayerDTO;
import com.example.comeonplayerserviceassignment.model.PlayerLogInRequest;
import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import com.example.comeonplayerserviceassignment.session.SessionEntity;
import com.example.comeonplayerserviceassignment.session.SessionRepository;
import com.example.comeonplayerserviceassignment.utils.ErrorModel;
import com.example.comeonplayerserviceassignment.utils.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    SessionRepository sessionRepository;
    CommonDTO commonDTO = new CommonDTO();


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

    public PlayerDTO playerLogIn(PlayerLogInRequest playerLogInRequest)
    {
        PlayerEntity player = playerRepository.findByEmail(playerLogInRequest.getEmail()).orElse(null);
        Optional<SessionEntity> activeSession = sessionRepository.findActiveSessionByPlayerId(player.getId());
        if(activeSession.isPresent())
        {
            if(activeSession.get().isLoggedIn())
            {
                ResponseEntity.status(HttpStatus.OK).body("Player is already Logged In");
            }
            else
            {
                if (activeSession.get().hasExceededTimeLimit())
                {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player already Exceeded the daily limits");
                }
                else
                {
                    activeSession.get().setStartTime(LocalDateTime.now());
                    activeSession.get().setLoggedIn(true);
                    sessionRepository.save(activeSession.get());
                }
            }
        }
        else
        {
            SessionEntity session = new SessionEntity(
                    player,
                    LocalDateTime.now(),
                    player.getDailyTimeLimit());
            sessionRepository.save(session);
            player.addSession(session);
            playerRepository.save(player);

        }
        return commonDTO.toPlayerDTO(player);

    }

    public PlayerDTO playerLogOut( String email)
    {
        PlayerEntity player = playerRepository.findByEmail(email).orElse(null);
        Optional<SessionEntity> activeSession = sessionRepository.findActiveSessionByPlayerId(player.getId());

        if (activeSession.isPresent() && activeSession.get().isLoggedIn())
        {
            activeSession.get().setLoggedIn(false);
            activeSession.get().setDailyTimeUsedByPlayerInMinutes(activeSession.get().getDailyTimeUsedByPlayerInMinutes());
            sessionRepository.save(activeSession.get());
            playerRepository.save(player);
            return commonDTO.toPlayerDTO(player);
        }
        else
        {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Player is not LoggedIn");
        }
        return null;
    }
}
