package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.user.RegisterDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public abstract class RegisterMapper {
    public abstract RegisterDto toDto(User user);

    @Mapping(target = "email", source = "username")
    public abstract User toEntity(RegisterDto userDto);
}
