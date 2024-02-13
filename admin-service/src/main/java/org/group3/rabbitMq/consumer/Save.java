package org.group3.rabbitMq.consumer;

import lombok.RequiredArgsConstructor;
import org.group3.entity.Admin;
import org.group3.rabbitMq.model.SaveAuthModel;
import org.group3.service.AdminService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Save {
    private final AdminService adminService;

    @RabbitListener(queues = "${rabbitmq.queue.admin.save}")
    public void saveFromQueue(SaveAuthModel model){
        adminService.save(Admin.builder()
                        .authid(model.getAuthid())
                        .email(model.getEmail())
                        .name(model.getName())
                        .surname(model.getSurname())
                        .phone(model.getPhone())
                .build());
    }


}
