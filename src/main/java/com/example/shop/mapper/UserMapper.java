package com.example.shop.mapper;

import com.example.shop.domain.User;
import com.example.shop.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToDto(User user);

    @Mapping(ignore = true, target = "accountCreated")
    User mapToEntity(UserDto userDto);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "accountCreated")
    User update(UserDto userDto, @MappingTarget User user);
}
