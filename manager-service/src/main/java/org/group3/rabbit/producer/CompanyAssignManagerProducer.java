package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.AssignManagerModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyAssignManagerProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.company}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.company.assignManager}")
    private String bindingKey;

    public void assignManager(AssignManagerModel model){
        rabbitTemplate.convertAndSend(exchange, bindingKey, model);
    }
}
