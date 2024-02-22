package org.group3.manager;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static org.group3.constant.EndPoints.FIND_NAME_BY_PERSONAL_ID;

@FeignClient(name = "personel-manager", url = "http://localhost:9095/personal" )
public interface IPersonelManager {

    @GetMapping(FIND_NAME_BY_PERSONAL_ID)
    ResponseEntity<String> findNameByPersonalId(@RequestParam Long id);
}
