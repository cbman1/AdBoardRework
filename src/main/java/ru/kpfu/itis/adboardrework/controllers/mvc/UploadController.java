package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
@PropertySource("classpath:application.properties")
public class UploadController {
    private final UserService userService;
    @PostMapping("/upload/avatar")
    public String uploadImage(Principal principal, Model model, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/my-profile";
        }

        // normalize the file path
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = StringUtils.cleanPath(UUID.randomUUID() + extension);

        // save the file on the local file system
        try {

            Path path = Paths.get("C:\\Users\\79174\\IdeaProjects\\AdBoardRework\\src\\main\\resources\\static\\images\\uploads\\avatar\\" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            userService.setUserAvatar(principal, fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/my-profile";
    }
}
