package org.group3.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbitmq.model.SendMailModel;
import org.group3.service.MailSenderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.queue.mail.sender}")
    public void sendMail(SendMailModel model){
        mailSenderService.sendMail(model);
    }
}
