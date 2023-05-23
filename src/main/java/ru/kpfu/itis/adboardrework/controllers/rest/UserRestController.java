package ru.kpfu.itis.adboardrework.controllers.rest;


import io.micrometer.core.instrument.config.validate.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.adboardrework.dto.user.NewUserDto;
import ru.kpfu.itis.adboardrework.services.UserService;

import javax.management.InstanceAlreadyExistsException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tags(value = {
        @Tag(name = "Users")
})
@Schema(description = "Работа с пользователем")
public class UserRestController {
    private final UserService userService;

    @Operation(summary = "регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "user",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NewUserDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "пользователь существует",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = InstanceAlreadyExistsException.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "невалидные данные",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ValidationException.class))
                    }
            )
    })
    @PostMapping
    ResponseEntity<?> addUser(@RequestBody NewUserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok().build();
    }
}
