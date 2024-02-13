package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping (SAVE)
    public ResponseEntity<String> save(@RequestBody SaveRequestDto dto){
        return ResponseEntity.ok(adminService.saveDto(dto));
    }
    @GetMapping(FIND_BY_ID + "/{id}")
    public ResponseEntity<FindByIdResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.findByIdDto(id));
    }

    @GetMapping(FIND_BY_AUTH_ID + "/{authid}")
    public ResponseEntity<FindByIdResponseDto> findByAuthId(@PathVariable Long authid){
        return ResponseEntity.ok(adminService.findByAuthIdDto(authid));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllResponseDto>> findAll(){
        return ResponseEntity.ok(adminService.findAllDto());
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(adminService.softUpdate(dto));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.softDelete(id));
    }


}
