package ru.kpfu.itis.adboardrework.services;

import ru.kpfu.itis.adboardrework.dto.user.NewUserDto;
import ru.kpfu.itis.adboardrework.dto.user.UpdateUserDto;
import ru.kpfu.itis.adboardrework.dto.user.UserDto;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.models.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void register(NewUserDto newUserDto);
    UserDto getUserDtoByEmail(String email);
    UserDto getUserDtoById(Long id);
    User getUserByEmail(String email);
    User getUserById(Long id);
    List<Advert> favoriteThisUser(Principal principal);
    void updateUser(String email, UpdateUserDto userDto);
    void deleteUser(Principal principal);
    void setUserAvatar(Principal principal, String avatarPath);
}
