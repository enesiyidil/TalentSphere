package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.entity.enums.EStatus;
import org.group3.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService service;

    @PostMapping("/save")
    public ResponseEntity<HolidayResponseDto> save(@RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<HolidayResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAllByCompanyId")
    public ResponseEntity<List<HolidayResponseDto>> findAllByCompanyId(@RequestParam Long companyId, EStatus status){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId, status));
    }

    @GetMapping("/findAllByPersonalId")
    public ResponseEntity<List<HolidayResponseDto>> findAllByPersonalId(@RequestParam Long companyId, @RequestParam Long personalId, EStatus status){
        return ResponseEntity.ok(service.findAllByPersonalId(companyId, personalId, status));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HolidayResponseDto> update(@PathVariable Long id, @RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

    @PostMapping("/setStatus")
    public ResponseEntity<HolidayResponseDto> setStatus(@RequestParam Long id, @RequestBody EStatus status){
        return ResponseEntity.ok(service.setStatus(id, status));
    }

}
