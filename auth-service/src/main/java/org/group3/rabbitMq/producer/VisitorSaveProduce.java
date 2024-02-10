package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.SaveAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VisitorSaveProduce {
    @Value("${rabbitmq.exchange.visitor}")
    private String visitorExchange;

    @Value("${rabbitmq.bindingKey.visitor.save}")
    private String visitorSaveBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public VisitorSaveProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SaveAuthModel model){
        rabbitTemplate.convertAndSend(visitorExchange,visitorSaveBindingKey,model);
    }
}
