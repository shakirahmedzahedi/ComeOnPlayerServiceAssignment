package com.example.comeonplayerserviceassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ComeOnPlayerServiceAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComeOnPlayerServiceAssignmentApplication.class, args);
    }

}
