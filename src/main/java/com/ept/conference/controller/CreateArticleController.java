package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.ThemeRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.CreateArticleService;
import com.ept.conference.service.FileService;
import com.ept.conference.service.FindPresentersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CreateArticleController {

    @Autowired
    private FileService fileService;

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ThemeRepository themeRepository;
    private final FindPresentersService findPresentersService;

    public CreateArticleController(ConferenceRepository conferenceRepository, UserRepository userRepository, ArticleRepository articleRepository, ThemeRepository themeRepository, FindPresentersService findPresentersService) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.themeRepository = themeRepository;
        this.findPresentersService = findPresentersService;
    }

    @RequestMapping(value = "/createArticle/{id}", method = RequestMethod.POST)
    public String addArticle(@PathVariable("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("theme") Optional<String[]> optThemes,
                             @RequestParam("user") Optional<String[]> optAuthors,
                             @RequestParam("pres") Optional<String[]> optPresenters,
                             @RequestParam("file") MultipartFile file,
                             Principal principal,
                             Model model){

        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());

        String[] themes;
        String[] authors;

        ArrayList[] pres = new ArrayList[0];
        pres = findPresentersService.findPresenters(optPresenters);

        ArrayList<String> foundPresenters =pres[0];
        ArrayList<String> notFoundPresenters = pres[1];

        if(!optAuthors.isPresent()){
            addAttributes(title, new String[0], description, model, user, new ArrayList<String>(), new ArrayList<String>());
            model.addAttribute("error", "authors can't be empty");
            return "addArticle";
        }else if(!optThemes.isPresent()) {
            addAttributes(title, new String[0], description, model, user, new ArrayList<String>(), new ArrayList<String>());
            model.addAttribute("error", "themes can't be empty");
            return "addArticle";
        }else if(notFoundPresenters.size() !=0){
            addAttributes(title, new String[0], description, model, user, new ArrayList<String>(), new ArrayList<String>());
            model.addAttribute("foundPresenters", foundPresenters);
            model.addAttribute("notFoundPresenters", notFoundPresenters);
            model.addAttribute("error", "one or more presenters do not exist");
            return "addArticle";
        }else if(file.isEmpty()){
            addAttributes(title, new String[0], description, model, user, new ArrayList<String>(), new ArrayList<String>());
            model.addAttribute("error", "Pdf file is required for evaluation");
            return "addArticle";
        }
        authors = optAuthors.get();
        themes = optThemes.get();

        CreateArticleService createArticleService = new CreateArticleService(articleRepository, conferenceRepository, userRepository, themeRepository);

        Conference conference = conferenceRepository.findById(id).get();

        ArrayList[] users = new ArrayList[0];
        try {
            users = createArticleService.createArticle(title, description, authors, themes, conference, file.getOriginalFilename(), optPresenters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> foundUsers = users[0];
        ArrayList<String> notFoundUsers = users[1];

        if(notFoundUsers.size() == 0){
            fileService.uploadFile(file);
            return "redirect:/articleList?success";
        }else{
            addAttributes(title, themes, description, model, user, foundUsers, notFoundUsers);
            model.addAttribute("error", "one or more users not found");
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
