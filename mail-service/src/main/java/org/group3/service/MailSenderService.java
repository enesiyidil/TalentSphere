package org.group3.service;

import lombok.RequiredArgsConstructor;
import org.group3.rabbitmq.model.SendMailModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(SendMailModel model) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("enesiyidil16@gmail.com");
        message.setTo(model.getEmail());
        message.setSubject(model.getSubject());
        message.setText(model.getContent());

        javaMailSender.send(message);
    }
}
