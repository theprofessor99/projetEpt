package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.TutorialRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.AddTutorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/addTuto/{id}")
public class AddTutorialController {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final TutorialRepository tutorialRepository;

    public AddTutorialController(ConferenceRepository conferenceRepository, UserRepository userRepository, TutorialRepository tutorialRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping
    public String getAddTuto(@PathVariable("id") Long id, Principal principal, Model model){

        model.addAttribute("id", id);

        return "addTuto";
    }

    @PostMapping
    public String addTuto(@PathVariable("id") Long id,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          Model model,
                          Principal principal){

        User user = userRepository.findByEmail(principal.getName());
        Conference conference = conferenceRepository.findById(id).get();

        model.addAttribute("id", id);

        try {
            AddTutorialService addTutorialService = new AddTutorialService(conferenceRepository, userRepository, tutorialRepository);
            addTutorialService.createTuto(title, description, user, conference);
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/addTuto/" +id + "?failure";
        }


        return "redirect:/addTuto/" + id + "?sucess";
    }

}
