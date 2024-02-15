package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.HolidayModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.personal}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.personal.addHoliday}")
    private String bindingKey;

    public void addHoliday(HolidayModel model){
        rabbitTemplate.convertAndSend(exchange, bindingKey, model);
    }
}
