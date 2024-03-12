package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.group3.constant.EndPoints.COMPANY;
import static org.group3.constant.EndPoints.FIND_NAME_BY_COMPANY_ID;

@FeignClient(name = "company-manager", url = "http://${company-service}:9097" + COMPANY )
//@FeignClient(name = "company-manager", url = "${apiGatewayUrl}" + COMPANY )
public interface ICompanyManager {

    @GetMapping(FIND_NAME_BY_COMPANY_ID)
    ResponseEntity<String> findNameByCompanyId(@RequestParam Long id);
}
