package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.adboardrework.services.ReviewsService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewsService reviewsService;
    @GetMapping("/my-profile")
    public String profile(Principal principal, Model model) {
        model.addAttribute("userDto", userService.getUserDtoByEmail(principal.getName()));
        return "profile";
    }


    //TODO: add view
    @GetMapping("/my-favorite")
    public String favorite(Principal principal, Model model) {
        model.addAttribute("favorites", userService.favoriteThisUser(principal));
        return "favorite";
    }

    @GetMapping("/profile")
    public String profileUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("userDto", userService.getUserDtoById(id));
        model.addAttribute("averageScore", String.format("%.2f%n", reviewsService.getAverageScore(id)));
        return "profile";
    }

}
