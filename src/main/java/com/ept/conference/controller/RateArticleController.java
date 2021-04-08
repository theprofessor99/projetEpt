package com.ept.conference.controller;

import com.ept.conference.model.Article;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.RateArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/rateArticle/{id}")
public class RateArticleController {

    public final ArticleRepository articleRepository;
    public final UserRepository userRepository;
    public final RateArticleService rateArticleService;

    public RateArticleController(ArticleRepository articleRepository, RateArticleService rateArticleService, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.rateArticleService = rateArticleService;
    }

    @PostMapping
    public String rateArticle(@PathVariable("id") Long id,@RequestParam("rating") float rating, Model model, Principal principal) {
        Article article = articleRepository.findById(id).get();
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());

        rateArticleService.rateArticle(article, user, rating);

        return "redirect:/articleList";

    }
}
