package ru.kpfu.itis.adboardrework.controllers.mvc;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.dto.advert.UpdateAdvertDto;
import ru.kpfu.itis.adboardrework.dto.user.UpdateUserDto;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService advertService;
    private final UserService userService;
    private final String PATH_UPLOAD_ADVERT_IMAGES = "C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\advert\\";

    @GetMapping("/advert")
    public String advertView(@RequestParam("id") Long id, Model model, Principal principal) {
        model.addAttribute("advert", advertService.getAdvertById(id));

        if (principal != null) {
            model.addAttribute("existFavorite", advertService.checkFavoriteUser(principal, id));
        }

        return "advert";
    }

    @GetMapping("/advert/add")
    public String addView(Model model, Principal principal) {

        model.addAttribute("errors", null);

        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

        model.addAttribute("advertDto", new AdvertDto());
        return "add-advert";
    }

    @PostMapping("/advert/add")
    public String addAdvert(@Valid @ModelAttribute(value = "advertDto") AdvertDto advertDto, Principal principal, @RequestParam("photos") MultipartFile[] photos, Model model,
                            BindingResult bindingResult) throws IOException {

        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "add-advert";
        }

        advertDto.setImages(setImages(photos));

        advertService.addAdvert(advertDto, principal);
        return "redirect:/advert?id=" + advertService.getIdLastAdvert(principal);
    }

    @GetMapping("/advert/edit")
    public String editView(@RequestParam("id") Long id, Model model, Principal principal) {

        model.addAttribute("errors", null);

        if (!Objects.equals(userService.getUserByEmail(principal.getName()).getId(), advertService.getAdvertById(id).getAuthorId().getId())) {
            model.addAttribute("errorMessage", "You are not author of this advert");
            return "error";
        }
        model.addAttribute("advert", advertService.getAdvertById(id));
        model.addAttribute("updateAdvertDto", new UpdateAdvertDto());
        return "edit-advert";
    }

    @PostMapping("/advert/edit")
    public String updateAdvert(@Valid @ModelAttribute("updateAdvertDto") UpdateAdvertDto updateAdvertDto,
                               Principal principal, @RequestParam(value = "files", required = false) MultipartFile[] files,
                               @RequestParam("id") Long id, Model model, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "edit-advert";
        }

        if (!Objects.equals(userService.getUserByEmail(principal.getName()).getId(), advertService.getAdvertById(id).getAuthorId().getId())) {
            model.addAttribute("errorMessage", "You are not author of this advert");
            return "error";
        }
        updateAdvertDto.setImages(setImages(files));
        advertService.updateAdvert(id , updateAdvertDto);
        return "redirect:/advert?id=" + id;
    }

    private List<String> setImages(MultipartFile[] files) throws IOException {
        List<String> images = new ArrayList<>();
        for (MultipartFile photo : files) {
            if (!Objects.equals(photo.getOriginalFilename(), "")) {
                String extension = Objects.requireNonNull(photo.getOriginalFilename()).substring(photo.getOriginalFilename().lastIndexOf("."));
                String fileName = StringUtils.cleanPath(UUID.randomUUID() + extension);
                Path path = Paths.get(PATH_UPLOAD_ADVERT_IMAGES + fileName);
                Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                images.add(fileName);
            }
        }
        return images;
    }


}
