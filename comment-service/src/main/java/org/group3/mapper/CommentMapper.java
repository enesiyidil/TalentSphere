package org.group3.mapper;

import org.group3.dto.request.CommentRequestDto;
import org.group3.dto.response.CommentFindAllByNotApproveResponse;
import org.group3.dto.response.CommentFindAllResponseDto;
import org.group3.dto.response.CommentResponseDto;
import org.group3.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment saveRequestDtoToComment(CommentRequestDto dto);

    CommentResponseDto commentToResponseDto(Comment comment);

    CommentFindAllByNotApproveResponse commentToCommentFindAllByNotApproveResponse(Comment comment);

    CommentFindAllResponseDto commentToCommentFindAllResponseDto(Comment comment);
}
