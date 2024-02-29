package org.group3.manager;

import org.group3.dto.response.CompanyFindAllInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.COMPANY;
import static org.group3.constant.EndPoints.FIND_ALL_INFO;

@FeignClient(name = "company-manager", url = "${apiGatewayUrl}" + COMPANY )
public interface ICompanyManager {

    @GetMapping(FIND_ALL_INFO)
    ResponseEntity<List<CompanyFindAllInfoResponseDto>> findAllInfo();
}
