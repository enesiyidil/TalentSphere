package org.group3.rabbitMq.producer;

import org.group3.rabbitMq.model.DeleteAuthModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthDeleteProducer {
    @Value("${rabbitmq.exchange.auth}")
    private String authExchange;

    @Value("${rabbitmq.bindingKey.auth.delete}")
    private String authDeleteBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public AuthDeleteProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(DeleteAuthModel model){
        rabbitTemplate.convertAndSend(authExchange,authDeleteBindingKey,model);
    }
}
