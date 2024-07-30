package com.app.Service;

import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTask {

    private final EmailService emailService;

    public Logger logger = Logger.getLogger(ScheduledTask.class);

    @Scheduled(cron = "30 * * * * ?")
    public void sendMail(){

        logger.info("In the scheduled task");

        emailService.sendEmail("saurab5719@gmail.com",
                "Testing the java mail",
                "Hi, this mail is send via springboot cron job and mail");
    }

}
