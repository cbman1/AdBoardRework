package ru.kpfu.itis.adboardrework.controllers.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tags(value = {
        @Tag(name = "Adverts")
})
@Schema(description = "Работа с объявлениями")
public class AdvertRestController {
    private final AdvertService advertService;
    private final UserService userService;

    @Operation(summary = "Получение всех объявлений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявления получены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdvertDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "Сведения о невалидных данных",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/api/advert")
    public ResponseEntity<List<Advert>> getAllAdverts() {
        return ResponseEntity.ok(advertService.getAllActiveAdverts());
    }




    @Operation(summary = "Получение одного объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление получено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdvertDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "Сведения о невалидных данных",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/api/advert/{id}")
    public ResponseEntity<AdvertDto> getAdvertById(@Parameter(name ="получение объявления по идентификатору", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(advertService.getAdvert(id));
    }


    @Operation(summary = "Добавление объявления в избранное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Объявление добавлено в избранное",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdvertDto.class))
                    }
            ),
            @ApiResponse(responseCode = "422", description = "Сведения о невалидных данных",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/advert/add/favorite")
    public ResponseEntity<?> addFavourite(@RequestParam("id") Long id, Principal principal) {
        advertService.addAdvertFavorite(id, principal);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/advert/remove/favorite")
    public ResponseEntity<?> removeFavourite(@RequestParam("id") Long id, Principal principal) {
        advertService.deleteAdvertFavorite(id, principal);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/advert/sold")
    public ResponseEntity<?> sold(@RequestParam("id") Long id, Principal principal) {
        advertService.advertSold(id, principal);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/advert/returnInSell")
    public ResponseEntity<?> returnInSell(@RequestParam("id") Long id, Principal principal) {
        advertService.returnInSell(id, principal);
        return ResponseEntity.accepted().build();
    }
}
