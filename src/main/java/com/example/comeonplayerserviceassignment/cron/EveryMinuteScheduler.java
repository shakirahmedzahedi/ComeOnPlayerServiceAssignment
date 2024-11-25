package com.example.comeonplayerserviceassignment.cron;

import com.example.comeonplayerserviceassignment.palyer.PlayerService;
import com.example.comeonplayerserviceassignment.session.SessionEntity;
import com.example.comeonplayerserviceassignment.session.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EveryMinuteScheduler
{
    private static final Logger logger = LoggerFactory.getLogger(EveryMinuteScheduler.class);

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    PlayerService playerService;

    @Scheduled(fixedRate = 60000)
    public void checkSessionInEveryMinute()
    {
        List<SessionEntity> sessionEntityList = sessionRepository.findAllActiveAndLoggedInSessions();

        if (sessionEntityList.size() > 0)
        {
            for (SessionEntity session : sessionEntityList)
            {
                session.updateDailyTimeForPlayer();
                sessionRepository.save(session);
                if (session.hasExceededTimeLimit())
                {
                    logger.info("PlayerId: " + session.getPlayer().getId() + " has Limit: "+ session.getPlayer().getDailyTimeLimit()+ " Exceed the Limit for today");
                    playerService.playerLogOut(session.getPlayer().getEmail());
                }
            }
        }
    }


}
