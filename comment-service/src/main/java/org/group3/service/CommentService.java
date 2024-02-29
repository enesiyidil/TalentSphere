package org.group3.service;

import org.group3.dto.request.AcceptOrRejectCommentByIdRequestDto;
import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CommentServiceException;
import org.group3.exception.ErrorType;
import org.group3.manager.ICompanyManager;
import org.group3.manager.IPersonalManager;
import org.group3.mapper.CommentMapper;
import org.group3.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repository;

    private final ICompanyManager companyManager;

    private final IPersonalManager personalManager;

    public CommentService(CommentRepository repository, ICompanyManager companyManager, IPersonalManager personalManager) {
        this.repository = repository;
        this.companyManager = companyManager;
        this.personalManager = personalManager;
    }

    public Boolean save(CommentRequestDto dto) {
        Comment comment = CommentMapper.INSTANCE.saveRequestDtoToComment(dto);
        repository.save(comment);
        return true;

    }

    public List<CommentFindAllByNotApproveResponse> findAllByNotApprove() {
        List<Comment> commentList = repository.findAllByStatusEquals(EStatus.PENDING);
        return commentList.stream().map(comment -> CommentFindAllByNotApproveResponse.builder()
                        .id(comment.getId())
                        .companyName(companyManager.findNameByCompanyId(comment.getCompanyId()).getBody())
                        .personalName(personalManager.findNameByPersonalId(comment.getPersonalId()).getBody())
                        .content(comment.getContent())
                        .title(comment.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    public Boolean acceptOrRejectCommentById(AcceptOrRejectCommentByIdRequestDto dto) {
        Optional<Comment> optionalComment = repository.findById(dto.getId());
        if (optionalComment.isPresent()){
            if ((optionalComment.get().getStatus().equals(EStatus.PENDING))){
                optionalComment.get().setStatus(Objects.equals(dto.getConfirm(), "accept") ? EStatus.ACCEPT : EStatus.REJECTED);
                repository.save(optionalComment.get());
                return true;
            }
            throw new CommentServiceException(ErrorType.COMMENT_NOT_PENDING);
        }
        throw new CommentServiceException(ErrorType.COMMENT_NOT_FOUND);
    }
    public List<CommentFindAllResponseDto> findAll() {
        List<Comment> commentList=repository.findAll();
        return commentList.stream().map(CommentMapper.INSTANCE::commentToCommentFindAllResponseDto)
                .collect(Collectors.toList());
    }

    public List<CommentFindAllByPersonalIdResponseDto> findAllByPersonalId(Long personalId) {
        return repository.findAllByPersonalId(personalId).stream()
                .filter(comment -> comment.getStatus().equals(EStatus.ACCEPT))
                .map(comment -> CommentFindAllByPersonalIdResponseDto.builder()
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate().toString())
                        .title(comment.getTitle())
                .build()).collect(Collectors.toList());
    }
    public List<CommentFindAllByCompanyIdResponseDto> findAllByCompanyId(Long companyId) {
        return repository.findAllByCompanyId(companyId).stream()
                .filter(comment -> comment.getStatus().equals(EStatus.ACCEPT))
                .map(comment -> CommentFindAllByCompanyIdResponseDto.builder()
                .personalId(comment.getPersonalId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate().toString())
                .build()).collect(Collectors.toList());
    }
}
