package org.group3.manager;

import org.group3.dto.response.PersonelResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL;

@FeignClient(name = "personel-manager", url = "http://localhost:9095/personal" )
public interface IPersonelManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<PersonelResponseDto>> findAll();
}
