package ru.kpfu.itis.adboardrework.controllers.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping({"/login"})
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        if (error!= null) {
            model.addAttribute("errorMessage", error);
        }

        return "login";
    }
}
