package ru.kpfu.itis.adboardrework.services;

import ru.kpfu.itis.adboardrework.dto.NewUserDto;
import ru.kpfu.itis.adboardrework.dto.UpdateUserDto;
import ru.kpfu.itis.adboardrework.dto.UserDto;

public interface UserService {
    void register(NewUserDto newUserDto);
    UserDto getUser(String email);
    void updateUser(String email, UpdateUserDto userDto);
    void deleteUser(String email);
}
