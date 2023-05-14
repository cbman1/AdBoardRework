package ru.kpfu.itis.adboardrework.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.cli.Digest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import ru.kpfu.itis.adboardrework.converters.UserMapper;
import ru.kpfu.itis.adboardrework.dto.NewUserDto;
import ru.kpfu.itis.adboardrework.dto.UpdateUserDto;
import ru.kpfu.itis.adboardrework.dto.UserDto;
import ru.kpfu.itis.adboardrework.models.Authority;
import ru.kpfu.itis.adboardrework.models.State;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.repositories.UserRepository;
import ru.kpfu.itis.adboardrework.security.details.UserDetailsImpl;
import ru.kpfu.itis.adboardrework.services.EmailService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;
    @Override
    public void register(NewUserDto newUserDto) {

        if (userRepository.existsUserByEmail(newUserDto.getEmail())) {
            throw new IllegalArgumentException("already exist");
        }


        User newUser = userRepository.save(User.builder()
                .firstName(newUserDto.getFirstName())
                .lastName(newUserDto.getLastName())
                .email(newUserDto.getEmail())
                .hashPassword(passwordEncoder.encode(newUserDto.getPassword()))
                .phoneNumber(newUserDto.getPhoneNumber())
                .hashForConfirm(DigestUtils.sha256Hex(newUserDto.getEmail() + UUID.randomUUID()))
                .state(State.NOT_CONFIRMED)
                .authority(Authority.ROLE_USER)
                .build());

        String confirmationLink = "http://localhost/email/confirm?accept=" +
                newUser.getHashForConfirm();

        emailService.sendEmail(newUserDto.getEmail(), "confirm", confirmationLink);
    }

    @Override
    public UserDto getUser(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found")));
    }

    @Override
    public void updateUser(String email, UpdateUserDto userDto) {

    }

    @Override
    public void deleteUser(String email) {

    }

    public static void updateAuthentication(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl principal) {
            if (principal.getUsername().equals(user.getEmail())) {
                UserDetailsImpl updatedPrincipal = new UserDetailsImpl(
                        User.builder().
                                id(user.getId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .hashPassword(user.getHashPassword())
                                .phoneNumber(user.getPhoneNumber())
                                .authority(user.getAuthority())
                                .state(user.getState())
                                .build()
                );
                Authentication newAuth = new UsernamePasswordAuthenticationToken(updatedPrincipal, auth.getCredentials(), updatedPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        }
    }
}
