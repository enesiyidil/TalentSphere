package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.BreakSaveRequestDto;
import org.group3.dto.request.BreakUpdateRequestDto;
import org.group3.dto.response.BreakResponseDto;
import org.group3.service.BreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/break")
@RequiredArgsConstructor
public class BreakController {

    private final BreakService service;

    @PostMapping("/save")
    public ResponseEntity<BreakResponseDto> save(@RequestBody BreakSaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<BreakResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAllByShiftId")
    public ResponseEntity<List<BreakResponseDto>> findAllByShiftId(@RequestParam Long shiftId){
        return ResponseEntity.ok(service.findAllByShiftId(shiftId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BreakResponseDto> update(@PathVariable Long id, @RequestBody BreakUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }
}
