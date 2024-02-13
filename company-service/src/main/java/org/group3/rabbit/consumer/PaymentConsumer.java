package org.group3.rabbit.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.rabbit.model.PaymentModel;
import org.group3.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    CompanyService companyService;

    @RabbitListener(queues = "${rabbitmq.queue.company.addPayment}")
    public void addPayment(PaymentModel model) {
        companyService.addPayment(model);
    }
}
