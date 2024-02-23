package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.CompanyFindByNameResponseDto;
import org.group3.dto.response.GetInformationForVisitorResponseDto;
import org.group3.dto.response.VisitorFindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.service.VisitorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.FIND_BY_AUTH_ID;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(VISITOR)
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FindByIdResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(visitorService.findByIdDto(id));
    }


    @GetMapping(FIND_ALL)
    public ResponseEntity<List<VisitorFindAllResponseDto>> findAll(){
        return ResponseEntity.ok(visitorService.findAllDto());
    }

    @PutMapping(UPDATE)
    public ResponseEntity<FindByIdResponseDto> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(visitorService.softUpdate(dto));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(visitorService.softDelete(id));
    }

    @GetMapping(FIND_BY_COMPANY_NAME)
    public ResponseEntity<CompanyFindByNameResponseDto> findByCompanyName(String companyName){
        return ResponseEntity.ok(visitorService.findByCompanyName(companyName));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<FindByIdResponseDto> findByAuthId(@RequestParam Long authId) {
        return ResponseEntity.ok(visitorService.findByAuthId(authId));
    }
    @GetMapping(GET_INFORMATION)
    public ResponseEntity<List<GetInformationForVisitorResponseDto>> getInformation(){
        return ResponseEntity.ok(visitorService.getInformation());
    }
}
