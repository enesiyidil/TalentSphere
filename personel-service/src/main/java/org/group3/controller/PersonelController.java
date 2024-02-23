package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PersonelSaveManagerRequestDto;
import org.group3.dto.request.PersonelSaveRequestDto;
import org.group3.dto.request.PersonelUpdateRequestDto;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.PersonelResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.repository.entity.Personel;
import org.group3.service.PersonelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RequestMapping(PERSONAL)
@RestController
@RequiredArgsConstructor
public class PersonelController {
    private final PersonelService personelService;

    @PostMapping(SAVE)
    public ResponseEntity<PersonelResponseDto> save(@RequestBody PersonelSaveRequestDto dto){
        return ResponseEntity.ok(personelService.save(dto));
    }

    @PostMapping(SAVE_MANAGER)
    public ResponseEntity<PersonelResponseDto> saveManager(@RequestBody PersonelSaveManagerRequestDto dto){
        return ResponseEntity.ok(personelService.saveManager(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<PersonelResponseDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(personelService.findById(id));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<PersonelResponseDto>> findAll() {
        return ResponseEntity.ok(personelService.findAll());
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deletePersonelById(@PathVariable Long id) {
        try {
            personelService.deletePersonelById(id);
            return ResponseEntity.ok(true);
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(false);
            }
        }
    }


@PutMapping(UPDATE)
    public ResponseEntity<PersonelResponseDto> updatePersonel(@RequestBody PersonelUpdateRequestDto dto) {
        try {
            PersonelResponseDto updatedPersonel = personelService.updatePersonel(dto);
            return ResponseEntity.ok(updatedPersonel);
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<PersonelResponseDto>> findAllByCompanyId(@RequestParam Long companyId) {
        return ResponseEntity.ok(personelService.findAllByCompanyId(companyId));
    }

    @GetMapping(FIND_ALL_PERSONAL_BY_COMPANY_ID)
    public ResponseEntity<List<Personel>> findAllPersonalByCompanyId(@RequestParam Long companyId) {
        return ResponseEntity.ok(personelService.findAllPersonalByCompanyId(companyId));
    }

    @GetMapping(FIND_ALL_BY_MANAGER_ID)
    public ResponseEntity<List<PersonelResponseDto>> findAllByManagerId(@RequestParam Long managerId) {
        return ResponseEntity.ok(personelService.findAllByManagerId(managerId));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<PersonelResponseDto> findByAuthId(@RequestParam Long authId) {
        return ResponseEntity.ok(personelService.findByAuthId(authId));
    }
    @GetMapping(FIND_NAME_BY_PERSONAL_ID)
    public ResponseEntity<String> findNameByPersonalId(@RequestParam Long id){
        return ResponseEntity.ok(personelService.findNameByPersonalId(id));
    }
    @GetMapping(GET_INFORMATION)
    public ResponseEntity<GetInformationResponseDto> getInformation(@RequestParam Long id){
        return ResponseEntity.ok(personelService.getInformation(id));
    }


}
