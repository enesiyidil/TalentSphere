package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.entity.enums.EStatus;
import org.group3.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.FIND_ALL;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(HOLIDAY)
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService service;

    @PostMapping(SAVE)
    public ResponseEntity<HolidayResponseDto> save(@RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<HolidayResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<HolidayResponseDto>> findAllByCompanyId(@RequestParam Long companyId, EStatus status){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId, status));
    }

    @GetMapping(FIND_ALL_BY_PERSONAL_ID)
    public ResponseEntity<List<HolidayResponseDto>> findAllByPersonalId(@RequestParam Long companyId, @RequestParam Long personalId, EStatus status){
        return ResponseEntity.ok(service.findAllByPersonalId(companyId, personalId, status));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<HolidayResponseDto> update(@RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @PostMapping(SET_STATUS)
    public ResponseEntity<HolidayResponseDto> setStatus(@RequestParam Long id, @RequestBody EStatus status){
        return ResponseEntity.ok(service.setStatus(id, status));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<HolidayResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllDto());
    }

}
