package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.CompanySaveRequestDto;
import org.group3.dto.request.CompanyUpdateRequestDto;
import org.group3.dto.response.CompanyResponseDto;
import org.group3.entity.Company;
import org.group3.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.FIND_ALL;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(COMPANY)
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @PostMapping(SAVE)
    public ResponseEntity<Company> save(@RequestBody CompanySaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Company> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL_BY_MANAGER_ID)
    public ResponseEntity<List<Company>> findAllByManagerId(@RequestParam Long managerId){
        return ResponseEntity.ok(service.findAllByManagerId(managerId));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<Company> update(@RequestBody CompanyUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Company>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(FIND_BY_PERSONAL_ID)
    public ResponseEntity<List<Company>> findByPersonalId(@RequestParam Long personalId){
        return ResponseEntity.ok(service.findByPersonalId(personalId));
    }
}
