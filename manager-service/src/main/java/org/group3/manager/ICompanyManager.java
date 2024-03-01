package org.group3.manager;

import org.group3.dto.response.Company;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.group3.constant.EndPoints.COMPANY;
import static org.group3.constant.EndPoints.FIND_BY_MANAGER_ID;

@FeignClient(name = "company-manager", url = "${apiGatewayUrl}" + COMPANY )
public interface ICompanyManager {

    @GetMapping(FIND_BY_MANAGER_ID)
    ResponseEntity<Company> findByManagerId(@RequestParam Long managerId);
}
