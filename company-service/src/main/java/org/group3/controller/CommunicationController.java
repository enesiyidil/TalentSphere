package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PhoneRequestDto;
import org.group3.dto.response.PhoneResponseDto;
import org.group3.service.CommunicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communication")
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService service;

    @PostMapping("/save")
    public ResponseEntity<PhoneResponseDto> save(@RequestBody PhoneRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<PhoneResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAllByCompanyId")
    public ResponseEntity<List<PhoneResponseDto>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PhoneResponseDto> update(@PathVariable Long id, @RequestBody PhoneRequestDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }
}
