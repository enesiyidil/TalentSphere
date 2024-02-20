package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.CommentResponseDto;
import org.group3.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(SAVE)
    public ResponseEntity<CommentResponseDto> save(@RequestBody CommentRequestDto dto){
        return ResponseEntity.ok(commentService.save(dto));
    }
}
