package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.AuthUpdateModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUpdateProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.auth}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.auth.update}")
    private String bindingKey;

    public void update(AuthUpdateModel model){
        rabbitTemplate.convertAndSend(exchange, bindingKey, model);
    }
}
