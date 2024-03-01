package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.ShiftSaveRequestDto;
import org.group3.dto.request.ShiftUpdateRequestDto;
import org.group3.dto.response.ShiftResponseDto;
import org.group3.entity.Shift;
import org.group3.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin("*")
@RestController
@RequestMapping(SHIFT)
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService service;

    @PostMapping(SAVE)
    public ResponseEntity<ShiftResponseDto> save(@RequestBody ShiftSaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Shift> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<ShiftResponseDto>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<ShiftResponseDto> update(@RequestBody ShiftUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<ShiftResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllDto());
    }

}
