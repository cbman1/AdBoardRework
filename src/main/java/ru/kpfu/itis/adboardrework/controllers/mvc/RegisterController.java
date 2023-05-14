package ru.kpfu.itis.adboardrework.controllers.mvc;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.adboardrework.dto.NewUserDto;
import ru.kpfu.itis.adboardrework.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    @GetMapping
    public String showRegistration(Model model) {
        model.addAttribute("newUserDto", new NewUserDto());
        return "/register";
    }
    @PostMapping
    public String register(@Valid @ModelAttribute("newUserDto") NewUserDto newUserDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        userService.register(newUserDto);
        return "redirect:/login";
    }
}
