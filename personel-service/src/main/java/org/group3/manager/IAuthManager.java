package org.group3.manager;

import jakarta.validation.Valid;
import org.group3.dto.request.RegisterRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.group3.constant.EndPoints.PERSONAL_SAVE;

@FeignClient(name = "auth-manager", url = "http://localhost:9092/auth")
public interface IAuthManager {

    @PostMapping(PERSONAL_SAVE)
    ResponseEntity<Long> personalSave(@RequestBody @Valid RegisterRequestDto dto);

}
