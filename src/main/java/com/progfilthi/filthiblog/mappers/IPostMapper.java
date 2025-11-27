package com.progfilthi.filthiblog.mappers;

import com.progfilthi.filthiblog.models.Post;
import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.models.dto.post.CreatePostDto;
import com.progfilthi.filthiblog.models.dto.post.PostAuthorDto;
import com.progfilthi.filthiblog.models.dto.post.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPostMapper {

    //Takes Entity converts it to DTO
    @Mapping(target = "author", source = "user")
    PostResponseDto toPostResponseDto(Post post);

    // Create DTO -> Entity (id + user set manually in service)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post toPostEntity(CreatePostDto dto);

    //Converting User -> PostAuthorDto
    PostAuthorDto toPostAuthorDto(User user);


}
