package org.group3.manager;

import org.group3.dto.response.Payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL_BY_COMPANY_ID;


@FeignClient(name = "payment-manager", url = "http://localhost:9098/payment" )
public interface IPaymentManager {

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    ResponseEntity<List<Payment>> findAllByCompanyId(@RequestParam Long companyId);
}
