package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.ConfernceRegistrationService;
import com.ept.conference.service.CreateArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class AddArticleController {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public AddArticleController(ConferenceRepository conferenceRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @RequestMapping(value = "/addArticle/{id}", method = RequestMethod.GET)
    public String getAddArticle(@PathVariable("id") Long id, Principal principal, Model model){

        User user = userRepository.findByEmail(principal.getName());
        Conference conference = conferenceRepository.findById(id).get();
        if(conference.getParticipants().contains(user) || conference.getAdmin() == user){
            model.addAttribute("id", id);
            return "addArticle";
        }else{
            return "redirect:/conference/" + id + "?noSub";
        }
    }


}
