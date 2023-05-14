package ru.kpfu.itis.adboardrework.controllers.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.adboardrework.services.EmailService;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailConfirmController {
    private final EmailService emailService;
    @GetMapping("/confirm")
    public String confirm(@RequestParam("accept") String hashForConfirm) {
        emailService.confirmAccount(hashForConfirm);
        return "redirect:/login";
    }
}
