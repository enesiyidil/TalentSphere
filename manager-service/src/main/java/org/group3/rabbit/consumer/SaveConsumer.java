package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.ManagerSaveModel;
import org.group3.service.ManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveConsumer {

    private final ManagerService service;

    @RabbitListener(queues = "${rabbitmq.queue.manager.save}")
    public void saveManager(ManagerSaveModel model){
        service.save(model);
    }
}
