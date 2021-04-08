package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/articleList")
public class ArticleListController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleListController(UserRepository userRepository, ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getMyArticles(Model model, Principal principal){

        String email = principal.getName();
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());

        model.addAttribute("myArticles", articleRepository.findArticleByAuthor(email));
        model.addAttribute("toReviewArticles", articleRepository.findArticleByReviewer(email));

        return "articleList";

    }

}
