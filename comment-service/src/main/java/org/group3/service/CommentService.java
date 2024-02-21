package org.group3.service;

import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.CommentResponseDto;
import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CommentManagerException;
import org.group3.exception.ErrorType;
import org.group3.mapper.CommentMapper;
import org.group3.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Comment> findAllByNotApprove() {
        return repository.findAllByStatusEquals(EStatus.PENDING);
    }

    public Comment acceptOrRejectCommentById(Long id, EStatus status) {
        Optional<Comment> optionalComment = repository.findById(id);
        if (optionalComment.isPresent()){
            if (!(optionalComment.get().getStatus().equals(EStatus.PENDING))){
                optionalComment.get().setStatus(status);
                repository.save(optionalComment.get());
                return optionalComment.get();
            }
            throw new CommentManagerException(ErrorType.COMMENT_NOT_PENDING);
        }
        throw new CommentManagerException(ErrorType.USER_NOT_FOUND);
    }
}
