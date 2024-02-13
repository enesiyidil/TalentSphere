package org.group3.rabbitMq.producer;


import org.group3.rabbitMq.model.SendMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailSenderProduce {
    @Value("${rabbitmq.exchange.mail}")
    private String mailExchange;

    @Value("${rabbitmq.bindingKey.mail.sender}")
    private String mailSenderBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public MailSenderProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void convertAndSend(SendMailModel model){
        rabbitTemplate.convertAndSend(mailExchange,mailSenderBindingKey,model);
    }
}
