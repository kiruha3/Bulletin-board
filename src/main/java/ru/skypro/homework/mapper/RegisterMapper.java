package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

import javax.validation.constraints.NotNull;

@Mapper(componentModel = "spring")
public abstract class RegisterMapper {
    public abstract RegisterDto toDto(User user);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phone", source = "phone")
    //@Mapping(target = "role", source = "role")
    @Mapping(target = "password", source = "password")
    public abstract User toEntity(RegisterDto userDto);
}
