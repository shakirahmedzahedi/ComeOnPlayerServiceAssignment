package com.example.comeonplayerserviceassignment.palyer;

import com.example.comeonplayerserviceassignment.cron.EveryMinuteScheduler;
import com.example.comeonplayerserviceassignment.dto.CommonDTO;
import com.example.comeonplayerserviceassignment.dto.PlayerDTO;
import com.example.comeonplayerserviceassignment.model.PlayerLogInRequest;
import com.example.comeonplayerserviceassignment.model.PlayerRegistrationRequest;
import com.example.comeonplayerserviceassignment.model.TimeLimitRequest;
import com.example.comeonplayerserviceassignment.session.SessionEntity;
import com.example.comeonplayerserviceassignment.session.SessionRepository;
import com.example.comeonplayerserviceassignment.utils.ErrorModel;
import com.example.comeonplayerserviceassignment.utils.ResponseWrapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlayerService
{
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    AuthenticationManager authManager;
    CommonDTO commonDTO = new CommonDTO();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);


    public ResponseWrapper<String> registerNewPlayer(PlayerRegistrationRequest playerRegistrationRequest)
    {
        logger.info("Request: "+ playerRegistrationRequest.toString());
        ResponseWrapper<String> response = new ResponseWrapper<>();
        boolean isExist = playerRepository.findByEmail(playerRegistrationRequest.getEmail()).isPresent();

        if (isExist)
        {
            response.addError(new ErrorModel("10001", "Email Already is use! "));
            return response;
        }
        PlayerEntity player = new PlayerEntity(
                playerRegistrationRequest.getEmail(),
                encoder.encode(playerRegistrationRequest.getPassword()),
                playerRegistrationRequest.getFirstName(),
                playerRegistrationRequest.getSurName(),
                playerRegistrationRequest.getDateOfBirth(),
                playerRegistrationRequest.getAddress());

        playerRepository.save(player);
        response.setData("Successfully register the player!");
        logger.info("Response: "+ response.toString());
        return response;
    }

    @Transactional
    public ResponseWrapper<PlayerDTO> playerLogIn(PlayerLogInRequest playerLogInRequest)
    {
        logger.info("Request: "+ playerLogInRequest.toString());
        ResponseWrapper<PlayerDTO> response = new ResponseWrapper<>();

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            playerLogInRequest.getEmail(),
                            playerLogInRequest.getPassword()));


            SecurityContextHolder.getContext().setAuthentication(authentication);

            PlayerEntity player = playerRepository.findByEmail(playerLogInRequest.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Player not found"));

            Optional<SessionEntity> activeSession = sessionRepository.findActiveSessionByPlayerId(player.getId());
            if (activeSession.isPresent())
            {
                SessionEntity session = activeSession.get();

                if (session.isLoggedIn())
                {
                    response.addError(new ErrorModel("10002", "Player is already Logged In"));
                }
                else if (session.hasExceededTimeLimit())
                {
                    response.addError(new ErrorModel("10003", "Player already exceeded the daily limits"));
                }
                else
                {
                    session.setStartTime(LocalDateTime.now());
                    session.setLoggedIn(true);
                    sessionRepository.save(session);
                    response.setData(commonDTO.toPlayerDTO(player));
                }
            }
            else
            {
                SessionEntity session = new SessionEntity(
                        player,
                        LocalDateTime.now(),
                        LocalDate.now());
                sessionRepository.save(session);
                player.addSession(session);
                playerRepository.save(player);

                response.setData(commonDTO.toPlayerDTO(player));
            }

        } catch (Exception ex) {
            response.addError(new ErrorModel("10005", "Invalid login credentials"));
        }

        return response;
    }


    public ResponseWrapper<PlayerDTO> playerLogOut(String email)
    {
        logger.info("Request parameter: "+ email);
        ResponseWrapper<PlayerDTO> response = new ResponseWrapper<>();
        try
        {

            PlayerEntity player = playerRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Player not found"));
            Optional<SessionEntity> activeSession = sessionRepository.findActiveSessionByPlayerId(player.getId());

            if (activeSession.isPresent() && activeSession.get().isLoggedIn())
            {
                activeSession.get().setLoggedIn(false);
                activeSession.get().setDailyTimeUsedByPlayerInMinutes(activeSession.get().getDailyTimeUsedByPlayerInMinutes());
                sessionRepository.save(activeSession.get());
                playerRepository.save(player);
                response.setData(commonDTO.toPlayerDTO(player));

            }
            else
            {
                response.addError(new ErrorModel("10006", "Player is not LoggedIn"));
                return response;
            }
            return response;
        }
        catch (EntityNotFoundException exception)
        {
            response.addError(new ErrorModel("10007", exception.getMessage()));

            return response;
        }
    }

    public ResponseWrapper<PlayerDTO> setTimeLimitForPlayer(TimeLimitRequest timeLimitRequest)
    {
        logger.info("Request: "+ timeLimitRequest.toString());
        ResponseWrapper<PlayerDTO> response = new ResponseWrapper<>();
        try
        {
            PlayerEntity player = playerRepository.findByEmail(timeLimitRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("Player not found"));
            Optional<SessionEntity> activeSession = sessionRepository.findActiveSessionByPlayerId(player.getId());

            if (activeSession.isPresent() && activeSession.get().isLoggedIn())
            {
                player.setDailyTimeLimit((int) (timeLimitRequest.getTimeLimitInHours()*60));
                playerRepository.save(player);
                response.setData(commonDTO.toPlayerDTO(player));
            }
            else
            {
                response.addError(new ErrorModel("10006", "Player is not LoggedIn"));
                return response;
            }
            return response;
        }
        catch (EntityNotFoundException exception)
        {
            response.addError(new ErrorModel("10007", exception.getMessage()));

            return response;
        }

    }
}
