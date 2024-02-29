package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import static org.group3.constant.EndPoints.FIND_NAME_BY_ID;
import static org.group3.constant.EndPoints.MANAGER;

@FeignClient(name = "manager-service-manager", url = "${apiGatewayUrl}" + MANAGER)
public interface IManagerServiceManager {

    @GetMapping(FIND_NAME_BY_ID)
    ResponseEntity<String> findNameById(@RequestParam Long id);
}
