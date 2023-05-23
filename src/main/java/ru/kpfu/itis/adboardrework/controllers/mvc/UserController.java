package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.ReviewsService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewsService reviewsService;
    private final AdvertService advertService;
    private static boolean checkActive = true;

    //TODO: add view
    @GetMapping("/my-favorite")
    public String favorite(Principal principal, Model model) {
        model.addAttribute("favorites", userService.favoriteThisUser(principal));
        return "favorite";
    }

    @GetMapping("/profile")
    public String profileUser(@RequestParam(value = "id", required = false) Long id, Model model, Principal principal) {
        model.addAttribute("isActive", checkActive);

        if (principal != null) {
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("myProfile", user);
            model.addAttribute("activeAdverts", advertService.getAllActiveAdvertsByUser(user.getId()));
            model.addAttribute("soldAdverts", advertService.getAllSoldAdvertsByUser(user.getId()));
            if (reviewsService.getAverageScore(user.getId()) != null) {
                model.addAttribute("myAverageScore", String.format("%.2f%n", reviewsService.getAverageScore(user.getId())));
            } else {
                model.addAttribute("myAverageScore", "No ratings");
            }
        }

        if (id != null) {
            model.addAttribute("userDto", userService.getUserDtoById(id));
            model.addAttribute("activeOtherAdverts", advertService.getAllActiveAdvertsByUser(id));
            model.addAttribute("soldOtherAdverts", advertService.getAllSoldAdvertsByUser(id));
            if (reviewsService.getAverageScore(id) != null) {
                model.addAttribute("averageScore", String.format("%.2f%n", reviewsService.getAverageScore(id)));
            } else {
                model.addAttribute("averageScore", "No ratings");
            }
        }

        return "profile";
    }

    @PostMapping("/profile")
    public String getStateAdverts(@RequestParam("isActive") Boolean isActive, @RequestParam("email") String email) {
        Long userId = userService.getUserByEmail(email).getId();
        checkActive = isActive;
        return "redirect:/profile?id=" + userId;
    }


    @GetMapping("/profile/edit")
    public String editProfile(Principal principal, Model model) {
        return "profile-edit";
    }

}
