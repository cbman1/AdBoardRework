package ru.kpfu.itis.adboardrework.controllers.mvc;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.services.AdvertService;
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
public class AdvertController {
    private final AdvertService advertService;
    private final UserService userService;
    private final String PATH_UPLOAD_ADVERT_IMAGES = "C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\advert\\";

    @GetMapping("/advert")
    public String advertView(@RequestParam("id") Long id, Model model, Principal principal) {
        model.addAttribute("advertDto", advertService.getAdvert(id));

        if (principal != null) {
            model.addAttribute("existFavorite", advertService.checkFavoriteUser(principal, id));
        }

        return "advert";
    }

    @GetMapping("/advert/add")
    public String addView(Model model) {
        model.addAttribute("advertDto", new AdvertDto());
        return "add-advert";
    }

    @PostMapping("/advert/add")
    public String addAdvert(@Valid @ModelAttribute(value = "advertDto") AdvertDto advertDto, Principal principal, @RequestParam("photos") MultipartFile[] photos) throws IOException {
            for (MultipartFile photo : photos) {
                if (!Objects.equals(photo.getOriginalFilename(), "")) {
                    String extension = Objects.requireNonNull(photo.getOriginalFilename()).substring(photo.getOriginalFilename().lastIndexOf("."));
                    String fileName = StringUtils.cleanPath(UUID.randomUUID() + extension);
                    Path path = Paths.get(PATH_UPLOAD_ADVERT_IMAGES + fileName);
                    Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                    advertDto.getImages().add(fileName);
                }
            }

        advertService.addAdvert(advertDto, principal);
        return "redirect:/advert?id=" + advertService.getIdLastAdvert(principal);
    }
}
