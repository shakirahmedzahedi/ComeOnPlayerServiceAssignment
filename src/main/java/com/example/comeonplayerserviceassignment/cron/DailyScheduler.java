package com.example.comeonplayerserviceassignment.cron;

import com.example.comeonplayerserviceassignment.palyer.PlayerRepository;
import com.example.comeonplayerserviceassignment.session.SessionEntity;
import com.example.comeonplayerserviceassignment.session.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyScheduler
{
    private static final Logger logger = LoggerFactory.getLogger(DailyScheduler.class);

    @Autowired
    private SessionRepository sessionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailySessionReset()
    {
        List<SessionEntity> sessionEntityList = sessionRepository.findAll();

        for (SessionEntity session : sessionEntityList)
        {
            session.setSessionDate(LocalDate.now());
            session.setDailyTimeUsedByPlayerInMinutes(0L);
            logger.info("Reset: "+ session.toString());
        }

        logger.info("Daily time limits have been reset for all players.");
    }
}