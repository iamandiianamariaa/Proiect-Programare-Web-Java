package com.example.shop.mapper;

import com.example.shop.domain.User;
import com.example.shop.domain.User.UserBuilder;
import com.example.shop.dto.UserDto;
import com.example.shop.dto.UserDto.UserDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-11T11:56:47+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto mapToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.username( user.getUsername() );
        userDto.name( user.getName() );
        userDto.userType( user.getUserType() );

        return userDto.build();
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.username( userDto.getUsername() );
        user.name( userDto.getName() );
        user.userType( userDto.getUserType() );

        return user.build();
    }

    @Override
    public User update(UserDto userDto, User user) {
        if ( userDto == null ) {
            return null;
        }

        user.setUsername( userDto.getUsername() );
        user.setName( userDto.getName() );
        user.setUserType( userDto.getUserType() );

        return user;
    }
}
