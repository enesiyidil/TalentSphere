package org.group3.manager;

import org.group3.dto.request.PersonalSaveManagerRequestDto;
import org.group3.dto.response.Personal;
import org.group3.dto.response.PersonalResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.SAVE_MANAGER;

@FeignClient(name = "personal-manager", url = "${apiGatewayUrl}" + PERSONAL )
public interface IPersonalManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<PersonalResponseDto>> findAll();

    @GetMapping(FIND_ALL_PERSONAL_BY_COMPANY_ID)
    ResponseEntity<List<Personal>> findAllPersonalByCompanyId(@RequestParam Long companyId);

    @PostMapping(SAVE_MANAGER)
    ResponseEntity<PersonalResponseDto> saveManager(@RequestBody PersonalSaveManagerRequestDto dto);

}
