package org.group3.manager;

import org.group3.dto.response.ManagerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL;
import static org.group3.constant.EndPoints.MANAGER;

@FeignClient(name = "manager-service-manager", url = "${apiGatewayUrl}" + MANAGER)
public interface IManagerServiceManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<ManagerResponseDto>> findAll();
}
