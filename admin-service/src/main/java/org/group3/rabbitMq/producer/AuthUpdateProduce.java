package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.UpdateAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthUpdateProduce {

    @Value("${rabbitmq.exchange.auth}")
    private String authExchange;

    @Value("${rabbitmq.bindingKey.auth.update}")
    private String authUpdateBindingKey;

    private final RabbitTemplate rabbitTemplate;


    public AuthUpdateProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(UpdateAuthModel model){
        rabbitTemplate.convertAndSend(authExchange,authUpdateBindingKey,model);
    }
}
