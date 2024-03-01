package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static org.group3.constant.EndPoints.FIND_NAME_BY_PERSONAL_ID;
import static org.group3.constant.EndPoints.PERSONAL;

@FeignClient(name = "personal-manager", url = "${apiGatewayUrl}" + PERSONAL )
public interface IPersonalManager {

    @GetMapping(FIND_NAME_BY_PERSONAL_ID)
    ResponseEntity<String> findNameByPersonalId(@RequestParam Long id);
}
