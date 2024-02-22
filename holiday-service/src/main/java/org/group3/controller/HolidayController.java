package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.dto.response.HolidayfFindAllByCompanyIdAndStatusPendingResponseDto;
import org.group3.entity.enums.EStatus;
import org.group3.service.HolidayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(HOLIDAY)
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService service;

    // checked but it will be update
    @PostMapping(SAVE)
    public ResponseEntity<HolidayResponseDto> save(@RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    // delete
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<HolidayResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    // maybe need it
    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public ResponseEntity<List<HolidayResponseDto>> findAllByCompanyId(@RequestParam Long companyId, EStatus status){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId, status));
    }

    // checked
    @GetMapping(FIND_ALL_BY_PERSONAL_ID)
    public ResponseEntity<List<HolidayResponseDto>> findAllByPersonalId(@RequestParam Long companyId, @RequestParam Long personalId, EStatus status){
        return ResponseEntity.ok(service.findAllByPersonalId(companyId, personalId, status));
    }

    // maybe need it
    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    // maybe need it
    @PatchMapping(UPDATE)
    public ResponseEntity<HolidayResponseDto> update(@RequestBody HolidayRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    // delete
    @PostMapping(SET_STATUS)
    public ResponseEntity<HolidayResponseDto> setStatus(@RequestParam Long id, @RequestBody EStatus status){
        return ResponseEntity.ok(service.setStatus(id, status));
    }

    // delete
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<HolidayResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllDto());
    }

    // half - checked
    @GetMapping(FIND_ALL_BY_COMPANY_ID_AND_STATUS_PANDING)
    public ResponseEntity<List<HolidayfFindAllByCompanyIdAndStatusPendingResponseDto>> findAllByCompanyIdAndStatusPending(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyIdAndStatusPending(companyId));
    }

    // Post request and dto
    @GetMapping (ACCEPT_OR_REJECT_HOLIDAY_BY_ID)
    public ResponseEntity<Boolean> acceptOrRejectHolidayById(@RequestParam Long id, String confirm){
        return ResponseEntity.ok(service.acceptOrRejectHolidayById(id, confirm));
    }

}
