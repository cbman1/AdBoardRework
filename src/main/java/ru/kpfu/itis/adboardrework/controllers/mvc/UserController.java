package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.adboardrework.dto.NewUserDto;
import ru.kpfu.itis.adboardrework.dto.UserDto;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/my-profile")
    public String profile(Principal principal, Model model) {
        model.addAttribute("userDto", userService.getUser(principal.getName()));
        return "profile";
    }

}
