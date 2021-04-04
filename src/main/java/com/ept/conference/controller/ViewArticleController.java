package com.ept.conference.controller;

import com.ept.conference.model.Article;
import com.ept.conference.repositories.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/{id}")
public class ViewArticleController {

    private final ArticleRepository articleRepository;

    public ViewArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public String viewArticle(@PathVariable("id") Long id, Model model){

        Article article = articleRepository.findById(id).get();

        model.addAttribute("article", article);
        model.addAttribute("view", true);

        return "article";
    }
}
