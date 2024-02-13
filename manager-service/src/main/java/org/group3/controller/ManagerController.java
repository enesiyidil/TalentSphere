package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.ManagerUpdateRequestDto;
import org.group3.dto.response.ManagerResponseDto;
import org.group3.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService service;

    @GetMapping("/findById")
    public ResponseEntity<ManagerResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findByAuthId")
    public ResponseEntity<ManagerResponseDto> findByAuthId(@RequestParam Long authId){
        return ResponseEntity.ok(service.findByAuthId(authId));
    }

    @PatchMapping("/update")
    public ResponseEntity<ManagerResponseDto> update(@RequestParam Long id, @RequestBody ManagerUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }
}
