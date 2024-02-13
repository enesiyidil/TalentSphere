package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.service.VisitorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody SaveRequestDto dto){
        return ResponseEntity.ok(visitorService.saveDto(dto));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<FindByIdResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(visitorService.findByIdDto(id));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<FindAllResponseDto>> findAll(){
        return ResponseEntity.ok(visitorService.findAllDto());
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(visitorService.softUpdate(dto));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(visitorService.softDelete(id));
    }
}
