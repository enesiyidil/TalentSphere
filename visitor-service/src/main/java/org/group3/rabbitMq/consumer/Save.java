package org.group3.rabbitMq.consumer;

import lombok.RequiredArgsConstructor;

import org.group3.entity.Visitor;
import org.group3.rabbitMq.model.SaveAuthModel;

import org.group3.service.VisitorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Save {
    private final VisitorService visitorService;

    @RabbitListener(queues = "${rabbitmq.queue.visitor.save}")
    public void saveFromQueue(SaveAuthModel model){
        visitorService.save(Visitor.builder()
                        .authid(model.getAuthid())
                        .email(model.getEmail())
                        .name(model.getName())
                        .surname(model.getSurname())
                        .phone(model.getPhone())
                .build());
    }


}
