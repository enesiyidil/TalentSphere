package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PersonalSaveManagerRequestDto;
import org.group3.dto.request.PersonalSaveRequestDto;
import org.group3.dto.request.PersonalUpdateRequestDto;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.PersonalResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.repository.entity.Personal;
import org.group3.service.PersonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin("*")
@RequestMapping(PERSONAL)
@RestController
@RequiredArgsConstructor
public class PersonalController {
    private final PersonalService personalService;

    @PostMapping(SAVE)
    public ResponseEntity<PersonalResponseDto> save(@RequestBody PersonalSaveRequestDto dto){
        return ResponseEntity.ok(personalService.save(dto));
    }

    @PostMapping(SAVE_MANAGER)
    public ResponseEntity<PersonalResponseDto> saveManager(@RequestBody PersonalSaveManagerRequestDto dto){
        return ResponseEntity.ok(personalService.saveManager(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<PersonalResponseDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(personalService.findById(id));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<PersonalResponseDto>> findAll() {
        return ResponseEntity.ok(personalService.findAll());
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deletePersonalById(@PathVariable Long id) {
        try {
            personalService.deletePersonalById(id);
            return ResponseEntity.ok(true);
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(false);
            }
        }
    }

@PatchMapping (UPDATE)
    public ResponseEntity<PersonalResponseDto> updatePersonal(@RequestBody PersonalUpdateRequestDto dto) {
        try {
            PersonalResponseDto updatedPersonal = personalService.updatePersonal(dto);
            return ResponseEntity.ok(updatedPersonal);
        } catch (PersonelServiceException e) {
            if (e.getErrorTypes().contains(ErrorType.USER_NOT_FOUND)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<PersonalResponseDto>> findAllByCompanyId(@RequestParam Long companyId) {
        return ResponseEntity.ok(personalService.findAllByCompanyId(companyId));
    }

    @GetMapping(FIND_ALL_PERSONAL_BY_COMPANY_ID)
    public ResponseEntity<List<Personal>> findAllPersonalByCompanyId(@RequestParam Long companyId) {
        return ResponseEntity.ok(personalService.findAllPersonalByCompanyId(companyId));
    }

    @GetMapping(FIND_ALL_BY_MANAGER_ID)
    public ResponseEntity<List<PersonalResponseDto>> findAllByManagerId(@RequestParam Long managerId) {
        return ResponseEntity.ok(personalService.findAllByManagerId(managerId));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<PersonalResponseDto> findByAuthId(@RequestParam Long authId) {
        return ResponseEntity.ok(personalService.findByAuthId(authId));
    }
    @GetMapping(FIND_NAME_BY_PERSONAL_ID)
    public ResponseEntity<String> findNameByPersonalId(@RequestParam Long id){
        return ResponseEntity.ok(personalService.findNameByPersonalId(id));
    }

    @GetMapping(FIND_NAME_BY_PERSONAL_AUTH_ID)
    public ResponseEntity<String> findNameByPersonalAuthId(@RequestParam Long authId){
        return ResponseEntity.ok(personalService.findNameByPersonalAuthId(authId));
    }
    @GetMapping(GET_INFORMATION)
    public ResponseEntity<GetInformationResponseDto> getInformation(@RequestParam Long id){
        return ResponseEntity.ok(personalService.getInformation(id));
    }


}
