package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.BreakSaveRequestDto;
import org.group3.dto.request.BreakUpdateRequestDto;
import org.group3.dto.response.BreakResponseDto;
import org.group3.service.BreakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@RestController
@RequestMapping(BREAK)
@RequiredArgsConstructor
public class BreakController {

    private final BreakService service;

    @PostMapping(SAVE)
    public ResponseEntity<BreakResponseDto> save(@RequestBody BreakSaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<BreakResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL_BY_SHIFT_ID)
    public ResponseEntity<List<BreakResponseDto>> findAllByShiftId(@RequestParam Long shiftId){
        return ResponseEntity.ok(service.findAllByShiftId(shiftId));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<BreakResponseDto> update(@RequestBody BreakUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }
}
