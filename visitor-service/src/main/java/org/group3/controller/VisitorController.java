package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.service.VisitorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(VISITOR)
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<FindByIdResponseDto> findById(@RequestParam Long id){
        return ResponseEntity.ok(visitorService.findByIdDto(id));
    }


    @GetMapping(FIND_ALL)
    public ResponseEntity<List<FindAllResponseDto>> findAll(){
        return ResponseEntity.ok(visitorService.findAllDto());
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> update(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(visitorService.softUpdate(dto));
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(visitorService.softDelete(id));
    }
}
