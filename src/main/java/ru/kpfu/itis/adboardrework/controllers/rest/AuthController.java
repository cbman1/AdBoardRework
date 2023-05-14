package ru.kpfu.itis.adboardrework.controllers.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @GetMapping("/auth")
    public ResponseEntity<?> isAuthenticated(Principal principal) {
        if (principal!= null) {
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED);
    }
}
