package org.group3.manager;

import org.group3.dto.response.PersonalResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL;
import static org.group3.constant.EndPoints.PERSONAL;

@FeignClient(name = "personal-manager", url = "${apiGatewayUrl}" + PERSONAL )
public interface IPersonalManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<PersonalResponseDto>> findAll();
}
