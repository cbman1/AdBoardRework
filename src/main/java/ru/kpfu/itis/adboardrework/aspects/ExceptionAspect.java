package ru.kpfu.itis.adboardrework.aspects;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kpfu.itis.adboardrework.exceptions.NotAllowedException;
import ru.kpfu.itis.adboardrework.exceptions.NotFoundException;


@ControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler(NotFoundException.class)
    public String handle(NotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NotAllowedException.class)
    public String handle(NotAllowedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
