package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDeleteProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.auth}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.auth.delete}")
    private String bindingKey;

    public void delete(Long authId){
        rabbitTemplate.convertAndSend(exchange, bindingKey, authId);
    }
}
