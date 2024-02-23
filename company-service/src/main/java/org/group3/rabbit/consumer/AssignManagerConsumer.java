package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.AssignManagerModel;
import org.group3.rabbit.model.HolidayModel;
import org.group3.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignManagerConsumer {

    private final CompanyService companyService;

    @RabbitListener(queues = "${rabbitmq.queue.company.assignManager}")
    public void assignManager(AssignManagerModel model) {
        companyService.assignManager(model);
    }
}
