package com.queueup.qup.service;

import com.queueup.qup.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    TokenRepo tokenRepo;

    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("queue.up4@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);


        mailSender.send(message);
    }
}
