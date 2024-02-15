package org.group3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PersonelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonelServiceApplication.class);
    }
}