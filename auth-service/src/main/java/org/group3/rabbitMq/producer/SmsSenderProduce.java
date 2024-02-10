package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.SmsSenderModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderProduce {

    @Value("${rabbitmq.exchange.sms}")
    private String smsExchange;

    @Value("${rabbitmq.bindingKey.sms.sender}")
    private String smsSenderBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public SmsSenderProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SmsSenderModel model) {
        rabbitTemplate.convertAndSend(smsExchange, smsSenderBindingKey, model);
    }
}
