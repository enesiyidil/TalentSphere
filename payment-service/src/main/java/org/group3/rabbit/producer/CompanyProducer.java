package org.group3.rabbit.producer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.PaymentModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.company}")
    private String exchange;

    @Value("${rabbitmq.bindingKey.company.addPayment}")
    private String bindingKey;

    public void addPayment(PaymentModel model){
        rabbitTemplate.convertAndSend(exchange, bindingKey, model);
    }
}
