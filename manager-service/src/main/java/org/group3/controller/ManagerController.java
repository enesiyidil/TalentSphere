package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.ManagerSaveRequestDto;
import org.group3.dto.request.ManagerUpdateRequestDto;
import org.group3.dto.response.Company;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.ManagerResponseDto;
import org.group3.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;
import static org.group3.constant.EndPoints.ADD_PERSONAL;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(MANAGER)
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService service;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> save(@RequestBody ManagerSaveRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<ManagerResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<ManagerResponseDto> findByAuthId(@RequestParam Long authId){
        return ResponseEntity.ok(service.findByAuthId(authId));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<ManagerResponseDto> update(@RequestBody ManagerUpdateRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<ManagerResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAllDto());
    }

    @GetMapping(GET_INFO_FOR_VISITOR)
    public ResponseEntity<List<Integer>> getInfoForVisitor(Long id){
        return ResponseEntity.ok(service.getInfoForVisitor(id));
    }

    @GetMapping(GET_INFORMATION)
    public ResponseEntity<GetInformationResponseDto> getInformation (@RequestParam Long id){
        return ResponseEntity.ok(service.getInformation(id));

    }

    @GetMapping(ADD_PERSONAL)
    public void addPersonal(@RequestParam Long managerId, @RequestParam Long personalId){
        service.addPersonal(managerId, personalId);
    }
    @GetMapping(FIND_NAME_BY_ID)
    public ResponseEntity<String> findNameById(@RequestParam Long id){
        return ResponseEntity.ok(service.findNameById(id));
    }

}
