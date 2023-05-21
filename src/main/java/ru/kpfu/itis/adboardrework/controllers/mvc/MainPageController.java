package ru.kpfu.itis.adboardrework.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainPageController {
    @GetMapping({"/", "/home"})
    public String mainPage(Model model) {
        return "main";
    }

}
