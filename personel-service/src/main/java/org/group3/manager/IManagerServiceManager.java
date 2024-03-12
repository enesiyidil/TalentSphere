package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.group3.constant.EndPoints.*;

@FeignClient(name = "manager-service-manager", url = "http://${manager-service}:9094" + MANAGER)
//@FeignClient(name = "manager-service-manager", url = "${apiGatewayUrl}" + MANAGER)
public interface IManagerServiceManager {

    @GetMapping(ADD_PERSONAL)
    void addPersonal(@RequestParam Long managerId, @RequestParam Long personalId);

    @GetMapping(FIND_NAME_BY_ID)
    ResponseEntity<String> findNameById(@RequestParam Long id);
}
