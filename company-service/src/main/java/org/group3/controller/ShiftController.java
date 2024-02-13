package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.ShiftSaveRequestDto;
import org.group3.dto.request.ShiftUpdateRequestDto;
import org.group3.dto.response.ShiftResponseDto;
import org.group3.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shift")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService service;

    @PostMapping("/save")
    public ResponseEntity<ShiftResponseDto> save(@RequestBody ShiftSaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<ShiftResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAllByCompanyId")
    public ResponseEntity<List<ShiftResponseDto>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ShiftResponseDto> update(@PathVariable Long id, @RequestBody ShiftUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }
}
