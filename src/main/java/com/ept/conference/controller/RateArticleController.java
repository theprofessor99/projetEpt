package com.ept.conference.controller;

import com.ept.conference.repositories.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rate{id}")
public class RateArticleController {

    public final ArticleRepository articleRepository;

    public RateArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public String ratePage(@PathVariable("id") Long id, Model model){
        model.addAttribute("article", articleRepository.findById(id));

        return "article";
    }

}
