package com.ept.conference.controller;

import com.ept.conference.model.Article;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/view/{id}")
public class ViewArticleController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ViewArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewArticle(@PathVariable("id") Long id, Model model, Principal principal){

        Article article = articleRepository.findById(id).get();

        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("article", article);
        model.addAttribute("view", true);

        return "article";
    }
}
