package org.group3.manager;


import org.group3.dto.request.ManagerSaveRequestDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.group3.constant.EndPoints.AUTH;
import static org.group3.constant.EndPoints.MANAGER_SAVE;

@FeignClient(name = "auth-manager", url = "http://${auth-service}:9092" + AUTH )
//@FeignClient(name = "auth-manager", url = "${apiGatewayUrl}" + AUTH )
public interface IAuthManager {

    @PostMapping(MANAGER_SAVE)
    ResponseEntity<Long> managerSave(@RequestBody ManagerSaveRequestDto dto);

}
