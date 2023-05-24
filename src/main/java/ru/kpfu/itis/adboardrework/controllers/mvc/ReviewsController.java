package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.adboardrework.dto.NewReviewDto;
import ru.kpfu.itis.adboardrework.dto.advert.AdvertDto;
import ru.kpfu.itis.adboardrework.services.AdvertService;
import ru.kpfu.itis.adboardrework.services.ReviewsService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReviewsController {
    private final AdvertService advertService;
    private final ReviewsService reviewsService;
    private final UserService userService;
    @GetMapping("/profile/reviews")
    public String reviews(@RequestParam("id") Long id, Model model, Principal principal) {
        model.addAttribute("emailReviews", userService.getUserById(id).getEmail());

        model.addAttribute("advertsUser", advertService.getAllAdvertsDtoByUser(id));
        model.addAttribute("reviews", reviewsService.getReviewsUser(id));
        model.addAttribute("newReviewDto", new NewReviewDto());
        return "reviews";
    }

    @PostMapping("/profile/reviews")
    public String addReview(@ModelAttribute(value = "newReviewDto") NewReviewDto reviewDto, @RequestParam("id") Long id, Principal principal, Model model) {
        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }
        reviewsService.addReview(reviewDto, id, principal);
        return "redirect:/profile/reviews?id=" + id;
    }
}
