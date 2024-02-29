package org.group3.rabbitMq.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbitMq.model.DeleteAuthModel;

import org.group3.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteConsumer {

    private final AuthService authService;
    @RabbitListener(queues = "${rabbitmq.queue.auth.delete}")
    public void saveFromQueue(DeleteAuthModel model){
        authService.softDelete(model.getAuthid());
    }
}
