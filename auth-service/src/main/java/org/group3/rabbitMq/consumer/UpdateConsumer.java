package org.group3.rabbitMq.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.entity.Auth;
import org.group3.rabbitMq.model.SaveAuthModel;
import org.group3.rabbitMq.model.UpdateAuthModel;
import org.group3.service.AuthService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConsumer {

    private final AuthService authService;
    @RabbitListener(queues = "${rabbitmq.queue.auth.update}")
    public void saveFromQueue(UpdateAuthModel model){
        authService.softUpdate(model);
    }
}
