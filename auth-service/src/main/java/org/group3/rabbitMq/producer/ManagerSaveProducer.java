package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.SaveAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ManagerSaveProducer {

    @Value("${rabbitmq.exchange.manager}")
    private String managerExchange;

    @Value("${rabbitmq.bindingKey.manager.save}")
    private String managerSaveBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public ManagerSaveProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SaveAuthModel model){
        rabbitTemplate.convertAndSend(managerExchange,managerSaveBindingKey,model);
    }
}
