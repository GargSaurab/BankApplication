package com.app.Service;

import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public Logger logger = Logger.getLogger(ScheduledTask.class);

    public void sendEmail(String to, String subject, String body)
    {
        logger.info("Sending the mail... " + to + subject + body);

        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);
        }catch (Exception e)
        {
            System.out.println("Exception while senEmail" + e);
        }
    }
}
