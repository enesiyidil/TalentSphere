package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.PersonalModel;
import org.group3.service.ManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonalConsumer {

    private final ManagerService service;

    @RabbitListener(queues = "${rabbitmq.queue.manager.addPersonal}")
    public void addPersonal(PersonalModel model){
        service.addPersonal(model);
    }

    @RabbitListener(queues = "${rabbitmq.queue.manager.deletePersonal}")
    public void deletePersonal(PersonalModel model){
        service.deletePersonal(model);
    }
}
