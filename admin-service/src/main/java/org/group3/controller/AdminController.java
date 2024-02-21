package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.UpdateResponseDto;
import org.group3.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(ADMIN)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping (SAVE)
    public ResponseEntity<String> save(@RequestBody SaveRequestDto dto){
        return ResponseEntity.ok(adminService.saveDto(dto));
    }
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FindByIdResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(adminService.findByIdDto(id));
    }

    @GetMapping(FIND_BY_AUTH_ID)
    public ResponseEntity<FindByIdResponseDto> findByAuthId(@RequestParam Long authid){
        return ResponseEntity.ok(adminService.findByAuthIdDto(authid));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllResponseDto>> findAll(){
        return ResponseEntity.ok(adminService.findAllDto());
    }

    @PutMapping(UPDATE)
    public ResponseEntity<UpdateResponseDto> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(adminService.softUpdate(dto));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(adminService.softDelete(id));
    }
    @GetMapping(GET_INFORMATION)
    public ResponseEntity<GetInformationResponseDto> getInformation (){
        return ResponseEntity.ok(adminService.getInformation());

    }


}
