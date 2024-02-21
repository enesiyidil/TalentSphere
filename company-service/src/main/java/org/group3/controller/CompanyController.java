package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.CompanySaveRequestDto;
import org.group3.dto.request.CompanyUpdateRequestDto;
import org.group3.dto.response.CompanyFindAllInfoResponseDto;
import org.group3.dto.response.CompanyFindAllWithoutManagerResponseDto;
import org.group3.dto.response.CompanyFindByNameResponseDto;
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

    // checked
    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody CompanySaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    // delete
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Company> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    // delete
    @GetMapping(FIND_ALL_BY_MANAGER_ID)
    public ResponseEntity<List<Company>> findAllByManagerId(@RequestParam Long managerId){
        return ResponseEntity.ok(service.findAllByManagerId(managerId));
    }

    // maybe need it
    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    // it will be update
    @PatchMapping(UPDATE)
    public ResponseEntity<Company> update(@RequestBody CompanyUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    // delete
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Company>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    // checked
    @GetMapping(FIND_ALL_INFO)
    public ResponseEntity<List<CompanyFindAllInfoResponseDto>> findAllInfo(){
        return ResponseEntity.ok(service.findAllInfo());
    }

    // it will be update
    @GetMapping(FIND_BY_PERSONAL_ID)
    public ResponseEntity<List<Company>> findByPersonalId(@RequestParam Long personalId){
        return ResponseEntity.ok(service.findByPersonalId(personalId));
    }

    // checked
    @GetMapping(FIND_ALL_WITHOUT_MANAGER)
    public ResponseEntity<List<CompanyFindAllWithoutManagerResponseDto>> findAllWithoutManager(){
        return ResponseEntity.ok(service.findAllWithoutManager());
    }

    // delete
    @GetMapping(FIND_BY_NAME)
    public ResponseEntity<CompanyFindByNameResponseDto> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }


}
