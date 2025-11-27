package com.progfilthi.filthiblog.mappers;

import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.models.dto.user.CreateUserDto;
import com.progfilthi.filthiblog.models.dto.user.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUserEntity(CreateUserDto dto);


    UserResponseDto toUserResponseDto(User user);
}
