package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.PersonalModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.manager}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.manager.deletePersonal}")
    private String bindingKey;

    public void deletePersonal(PersonalModel model){
        rabbitTemplate.convertAndSend(exchange, bindingKey, model);
    }
}
