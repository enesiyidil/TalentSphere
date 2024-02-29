package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PhoneRequestDto;
import org.group3.dto.response.PhoneResponseDto;
import org.group3.dto.response.ShiftResponseDto;
import org.group3.service.CommunicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin("*")
@RestController
@RequestMapping(COMMUNICATION)
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService service;

    @PostMapping(SAVE)
    public ResponseEntity<PhoneResponseDto> save(@RequestBody PhoneRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<PhoneResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<PhoneResponseDto>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<PhoneResponseDto> update(@RequestBody PhoneRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<PhoneResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllDto());
    }
}
