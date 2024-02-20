package org.group3.manager;

import jakarta.validation.Valid;
import org.group3.dto.request.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.group3.constant.EndPoints.MANAGER_SAVE;


@FeignClient(name = "auth-manager", url = "http://localhost:9092/auth")
public interface IAuthManager {

    @PostMapping(MANAGER_SAVE)
    ResponseEntity<Long> managerSave(@RequestBody @Valid RegisterRequestDto dto);

}
