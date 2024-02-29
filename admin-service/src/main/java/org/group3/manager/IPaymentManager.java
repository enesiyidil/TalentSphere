package org.group3.manager;

import org.group3.dto.response.PaymentFindAllInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL_INFO;
import static org.group3.constant.EndPoints.PAYMENT;

@FeignClient(name = "payment-manager", url = "${apiGatewayUrl}" + PAYMENT )
public interface IPaymentManager {

    @GetMapping(FIND_ALL_INFO)
    ResponseEntity<List<PaymentFindAllInfoResponseDto>> findAllInfo();
}
