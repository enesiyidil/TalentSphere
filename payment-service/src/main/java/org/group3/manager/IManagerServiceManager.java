package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import static org.group3.constant.EndPoints.*;

@FeignClient(name = "manager-service-manager", url = "${apiGatewayUrl}" + MANAGER)
public interface IManagerServiceManager {

    @GetMapping(FIND_NAME_BY_AUTH_ID)
    ResponseEntity<String> findNameByAuthId(@RequestParam Long authId);
}
