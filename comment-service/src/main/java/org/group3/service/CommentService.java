package org.group3.service;

import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.CommentFindAllByNotApproveResponse;
import org.group3.dto.response.CommentFindAllResponseDto;
import org.group3.dto.response.CommentResponseDto;
import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CommentServiceException;
import org.group3.exception.ErrorType;
import org.group3.mapper.CommentMapper;
import org.group3.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public CommentResponseDto save(CommentRequestDto dto) {
        Comment comment = CommentMapper.INSTANCE.saveRequestDtoToComment(dto);
        //adminin active koşulu gelsin

        repository.save(comment);

        return CommentMapper.INSTANCE.commentToResponseDto(comment);

    }

    public List<CommentFindAllByNotApproveResponse> findAllByNotApprove() {
        List<Comment> commentList = repository.findAllByStatusEquals(EStatus.PENDING);
        return commentList.stream().map(CommentMapper.INSTANCE::commentToCommentFindAllByNotApproveResponse)
                .collect(Collectors.toList());
    }

    public Boolean acceptOrRejectCommentById(Long id, EStatus status) {
        Optional<Comment> optionalComment = repository.findById(id);
        if (optionalComment.isPresent()){
            if ((optionalComment.get().getStatus().equals(EStatus.PENDING))){
                optionalComment.get().setStatus(status);
                repository.save(optionalComment.get());
                return true;
            }
            throw new CommentServiceException(ErrorType.COMMENT_NOT_PENDING);
        }
        throw new CommentServiceException(ErrorType.COMMENT_NOT_FOUND);
    }

    public Comment findById(Long id) {
        Optional<Comment> optionalComment = repository.findById(id);
        if (optionalComment.isPresent()) {

            return optionalComment.get();
        }
        throw new CommentServiceException(ErrorType.COMMENT_NOT_FOUND);
    }

    public List<CommentFindAllResponseDto> findAll() {
        List<Comment> commentList=repository.findAll();
        return commentList.stream().map(CommentMapper.INSTANCE::commentToCommentFindAllResponseDto)
                .collect(Collectors.toList());
    }
}
