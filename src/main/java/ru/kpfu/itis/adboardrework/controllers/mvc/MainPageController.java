package ru.kpfu.itis.adboardrework.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.adboardrework.services.AdvertService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final AdvertService advertService;
    @GetMapping({"/", "/home"})
    public String mainPage(Model model, Principal principal) {
        model.addAttribute("allActiveAdverts", advertService.getAllActiveAdverts());
        return "main";
    }

}
