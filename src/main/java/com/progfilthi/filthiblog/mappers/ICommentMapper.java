package com.progfilthi.filthiblog.mappers;

import com.progfilthi.filthiblog.models.Comment;
import com.progfilthi.filthiblog.models.dto.comment.CommentResponseDto;
import com.progfilthi.filthiblog.models.dto.comment.CreateCommentDto;
import com.progfilthi.filthiblog.models.dto.comment.UpdateCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ICommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Comment toCommentEntity(CreateCommentDto dto);



    CommentResponseDto toCommentResponseDto(Comment comment);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromUpdateCommentDto(UpdateCommentDto dto, @MappingTarget Comment comment);
}
