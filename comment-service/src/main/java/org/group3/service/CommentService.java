package org.group3.service;

import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.CommentResponseDto;
import org.group3.entity.Comment;
import org.group3.mapper.CommentMapper;
import org.group3.repository.CommentRepository;
import org.springframework.stereotype.Service;

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
}
