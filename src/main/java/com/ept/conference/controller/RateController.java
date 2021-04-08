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
@RequestMapping("/rate/{id}")
public class RateController {

    public final ArticleRepository articleRepository;
    public final UserRepository userRepository;
    public final RateArticleService rateArticleService;

    public RateController(ArticleRepository articleRepository, RateArticleService rateArticleService, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.rateArticleService = rateArticleService;
    }

    @GetMapping
    public String ratePage(@PathVariable("id") Long id, Model model, Principal principal){

        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("article", articleRepository.findById(id).get());
        model.addAttribute("view", false);
        return "article";
    }
}
