package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.SmsSenderModel;
import org.group3.service.TwilioSmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SenderConsumer {

    private final TwilioSmsService service;

    @RabbitListener(queues = "${rabbitmq.queue.sms.sender}")
    public void addCompany(SmsSenderModel model){
        service.sendSms(model.getToNumber(), model.getMessage());
    }

}
