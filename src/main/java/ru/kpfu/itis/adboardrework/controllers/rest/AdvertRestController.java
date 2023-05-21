package ru.kpfu.itis.adboardrework.controllers.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AdvertRestController {
    private final AdvertService advertService;
    private final UserService userService;
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
