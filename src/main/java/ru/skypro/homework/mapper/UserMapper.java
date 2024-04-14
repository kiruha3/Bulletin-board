package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.ImageRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private ImageRepository imageRepository;

    @Mapping(target = "image", expression = "java(user.getImage().getUrl())")
    abstract UserDto toDto(User user);
//    abstract User toEntity(UserDto userDto);
}
