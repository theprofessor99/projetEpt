package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.ThemeRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.CreateArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class CreateArticleController {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ThemeRepository themeRepository;

    public CreateArticleController(ConferenceRepository conferenceRepository, UserRepository userRepository, ArticleRepository articleRepository, ThemeRepository themeRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.themeRepository = themeRepository;
    }

    @RequestMapping(value = "/createArticle/{id}", method = RequestMethod.POST)
    public String addArticle(@PathVariable("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("theme") String[] themes,
                             @RequestParam("user") String[] authors,
                             Principal principal,
                             Model model){

        CreateArticleService createArticleService = new CreateArticleService(articleRepository, conferenceRepository, userRepository, themeRepository);

        User user = userRepository.findByEmail(principal.getName());
        Conference conference = conferenceRepository.findById(id).get();

        ArrayList[] users = new ArrayList[0];
        try {
            users = createArticleService.createArticle(title, description, authors, themes, conference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> foundUsers = users[0];
        ArrayList<String> notFoundUsers = users[1];

        if(notFoundUsers.size() == 0){
            return "redirect:/articleList?success";
        }else{
            addAttributes(title, themes, description, model, user, foundUsers, notFoundUsers);

            return "addArticle";
        }
    }

    private void addAttributes(String title,
                               String[] themes,
                               String description,
                               Model model,
                               User user,
                               ArrayList<String> foundUsers,
                               ArrayList<String> notFoundUsers) {
        model.addAttribute("notFoundUsers", notFoundUsers);
        model.addAttribute("foundUsers", foundUsers);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("themes", themes);
        model.addAttribute("fail", true);
    }

}
