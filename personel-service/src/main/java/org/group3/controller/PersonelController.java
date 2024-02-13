package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PersonelSaveRequestDto;
import org.group3.dto.request.PersonelUpdateRequestDto;
import org.group3.dto.response.PersonelResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.service.PersonelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/personel")
@RestController
@RequiredArgsConstructor
public class PersonelController {
    private final PersonelService personelService;

    @PostMapping("/save")
    public ResponseEntity<PersonelResponseDto> save(@RequestBody PersonelSaveRequestDto dto){
        return ResponseEntity.ok(personelService.save(dto));
    }
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<PersonelResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(personelService.findById(id));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<PersonelResponseDto>> findAll() {
        return ResponseEntity.ok(personelService.findAll());
    }

    @DeleteMapping("/personel/{id}")
    public ResponseEntity<String> deletePersonelById(@PathVariable Long id) {
        try {
            personelService.deletePersonelById(id);
            return ResponseEntity.ok("Personel with ID " + id + " has been deleted.");
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the personel.");
            }
        }
    }


@PutMapping("/update/{id}")
    public ResponseEntity<PersonelResponseDto> updatePersonel(@PathVariable("id") Long id,
                                                              @RequestBody PersonelUpdateRequestDto dto) {
        try {
            PersonelResponseDto updatedPersonel = personelService.updatePersonel(id, dto);
            return ResponseEntity.ok(updatedPersonel);
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
    @GetMapping("/findByCompanyId/{id}")
    public ResponseEntity<List<PersonelResponseDto>> findByCompanyId(@PathVariable("id")Long companyId) {
        return ResponseEntity.ok(personelService.findByCompanyId(companyId));
    }

    @GetMapping("/findByManagerId/{id}")
    public ResponseEntity<List<PersonelResponseDto>> findByManagerId(@PathVariable("id") Long managerId) {
        return ResponseEntity.ok(personelService.findAllByManagerId(managerId));
    }

    @GetMapping("/findByAuthId/{id}")
    public ResponseEntity<List<PersonelResponseDto>> findByAuthId(@PathVariable("id") Long authId) {
        return ResponseEntity.ok(personelService.findByAuthId(authId));
    }
}
