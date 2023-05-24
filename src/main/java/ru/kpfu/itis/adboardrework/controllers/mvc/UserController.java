package ru.kpfu.itis.adboardrework.controllers.mvc;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.adboardrework.dto.user.UpdateUserDto;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.ReviewsService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewsService reviewsService;
    private final AdvertService advertService;
    private static boolean checkActive = true;
    private final String PATH_UPLOAD_AVATAR_IMAGES = "C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\avatar\\";

    //TODO: add view
    @GetMapping("/my-favorite")
    public String favorite(Principal principal, Model model) {

        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

        model.addAttribute("favorites", userService.favoriteThisUser(principal));
        return "favorite";
    }

    @GetMapping("/profile")
    public String profileUser(@RequestParam(value = "id", required = false) Long id, Model model, Principal principal) {
        model.addAttribute("isActive", checkActive);

        if (principal == null && id == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

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
    public String editProfileView(Principal principal, Model model) {
        model.addAttribute("updateUserDto", new UpdateUserDto());
        model.addAttribute("errors", null);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@Valid @ModelAttribute("updateUserDto") UpdateUserDto updateUserDto, Principal principal, @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ,BindingResult bindingResult, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "profile-edit";
        }

        if (!Objects.equals(avatar.getOriginalFilename(), "")) {
            String extension = Objects.requireNonNull(avatar.getOriginalFilename()).substring(avatar.getOriginalFilename().lastIndexOf("."));
            String fileName = StringUtils.cleanPath(UUID.randomUUID() + extension);
            Path path = Paths.get(PATH_UPLOAD_AVATAR_IMAGES + fileName);
            Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            updateUserDto.setAvatarPath(fileName);
        }

        userService.updateUser(principal.getName(), updateUserDto);

        return "redirect:/profile";
    }


}
