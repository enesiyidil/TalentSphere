package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.AcceptOrRejectCommentByIdRequestDto;
import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.group3.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // checked
    @PostMapping(SAVE_BY_PERSONAL)
    public ResponseEntity<CommentResponseDto> save(@RequestBody CommentRequestDto dto){
        return ResponseEntity.ok(commentService.save(dto));
    }

    // half - checked
    @GetMapping (FIND_ALL_BY_APPROVE)
    public ResponseEntity<List<CommentFindAllByNotApproveResponse>> findAllByNotApprove(){
        return ResponseEntity.ok(commentService.findAllByNotApprove());
    }

    // checked
    @PostMapping (ACCEPT_OR_REJECT_COMMENT_BY_ID)
    public ResponseEntity<Boolean> acceptOrRejectCommentById(@RequestBody AcceptOrRejectCommentByIdRequestDto dto){
        return ResponseEntity.ok(commentService.acceptOrRejectCommentById(dto));
    }

    // checked
    @GetMapping (FIND_ALL)
    public  ResponseEntity<List<CommentFindAllResponseDto>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping (FIND_ALL_BY_PERSONAL_ID)
    public  ResponseEntity<List<CommentFindAllByPersonalIdResponseDto>> findAllByPersonalId(@RequestParam Long personalId){
        return ResponseEntity.ok(commentService.findAllByPersonalId(personalId));
    }

    @GetMapping (FIND_ALL_BY_COMPANY_ID)
    public  ResponseEntity<List<CommentFindAllByCompanyIdResponseDto>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(commentService.findAllByCompanyId(companyId));
    }


}
