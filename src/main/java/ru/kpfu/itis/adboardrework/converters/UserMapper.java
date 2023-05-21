package ru.kpfu.itis.adboardrework.converters;

import org.mapstruct.Mapper;
import ru.kpfu.itis.adboardrework.dto.user.UserDto;
import ru.kpfu.itis.adboardrework.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
