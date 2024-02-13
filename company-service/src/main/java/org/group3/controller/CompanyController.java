package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.CompanySaveRequestDto;
import org.group3.dto.request.CompanyUpdateRequestDto;
import org.group3.entity.Company;
import org.group3.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @PostMapping("/save")
    public ResponseEntity<Company> save(@RequestBody CompanySaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<Company> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findAllByManagerId")
    public ResponseEntity<List<Company>> findAllByManagerId(@RequestParam Long managerId){
        return ResponseEntity.ok(service.findAllByManagerId(managerId));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Company> update(@PathVariable Long id, @RequestBody CompanyUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(id,dto));
    }
}
