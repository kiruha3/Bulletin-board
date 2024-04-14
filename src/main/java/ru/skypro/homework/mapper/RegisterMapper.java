package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = "spring")
public abstract class RegisterMapper {
    public abstract RegisterDto toDto(User user);
    public abstract User toEntity(RegisterDto userDto);
}
