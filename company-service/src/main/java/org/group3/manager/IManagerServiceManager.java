package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



import static org.group3.constant.EndPoints.FIND_NAME_BY_ID;

@FeignClient(name = "manager-service-manager", url = "http://localhost:9094/manager")
public interface IManagerServiceManager {

    @GetMapping(FIND_NAME_BY_ID)
    ResponseEntity<String> findNameById(@RequestParam Long id);
}
