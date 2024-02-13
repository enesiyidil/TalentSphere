package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.CompanyModel;
import org.group3.service.ManagerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyConsumer {

    private final ManagerService service;

    @RabbitListener(queues = "${rabbitmq.queue.manager.addCompany}")
    public void addCompany(CompanyModel model){
        service.addCompany(model);
    }

}
