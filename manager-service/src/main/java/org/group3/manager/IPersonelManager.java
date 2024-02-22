package org.group3.manager;

import org.group3.dto.request.PersonelSaveManagerRequestDto;
import org.group3.dto.response.Personel;
import org.group3.dto.response.PersonelResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.SAVE_MANAGER;

@FeignClient(name = "personel-manager", url = "http://localhost:9095/personal" )
public interface IPersonelManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<PersonelResponseDto>> findAll();

    @GetMapping(FIND_ALL_PERSONAL_BY_COMPANY_ID)
    ResponseEntity<List<Personel>> findAllPersonalByCompanyId(@RequestParam Long companyId);

    @PostMapping(SAVE_MANAGER)
    ResponseEntity<PersonelResponseDto> saveManager(@RequestBody PersonelSaveManagerRequestDto dto);

}
