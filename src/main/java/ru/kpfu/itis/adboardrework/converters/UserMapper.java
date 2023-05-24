package ru.kpfu.itis.adboardrework.converters;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import ru.kpfu.itis.adboardrework.dto.user.UserDto;
import ru.kpfu.itis.adboardrework.models.User;

@Mapper(uses = {User.class},componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
