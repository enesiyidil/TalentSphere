package org.group3.manager;


import org.group3.dto.response.PaymentInformationForVisitorResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import static org.group3.constant.EndPoints.GET_INFORMATION_FOR_VISITOR;
import static org.group3.constant.EndPoints.PAYMENT;

@FeignClient(name = "payment-manager", url = "${apiGatewayUrl}" + PAYMENT )
public interface IPaymentManager {

    @GetMapping(GET_INFORMATION_FOR_VISITOR)
    ResponseEntity<PaymentInformationForVisitorResponseDto> getInformationForVisitor(@RequestParam Long companyId);
}
