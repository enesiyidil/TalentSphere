package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.SaveAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminSave {

    @Value("${rabbitmq.exchange.admin}")
    private String adminExchange;

    @Value("${rabbitmq.bindingKey.admin.save}")
    private String adminSaveBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public AdminSave(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SaveAuthModel model){
        rabbitTemplate.convertAndSend(adminExchange,adminSaveBindingKey,model);
    }
}
