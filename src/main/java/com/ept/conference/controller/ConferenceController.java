package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Session;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.DayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
@RequestMapping("/conference/{id}")
public class ConferenceController {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;

    public ConferenceController(ConferenceRepository conferenceRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getConference(@PathVariable("id") Long id, Model model, Principal principal){

        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());
        Conference conference = conferenceRepository.findById(id).get();

        if(user.getConferences().contains(conference)){
            model.addAttribute("admin", true);

        }else{
            model.addAttribute("admin", false);
        }

        if(user.getSubscribedConferences().contains(conference)){
            model.addAttribute("sub", true);

        }else{
            model.addAttribute("sub", false);
        }

        ArrayList<DayService> days = DayService.getDays(conference);

        /*ArrayList<Integer> index = new ArrayList<>();
        int k = 0;
        for(DayService day : days) {
            index.add(k);
            k++;
        }*/


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");

        model.addAttribute("conference", conference);
        model.addAttribute("user", user);
        model.addAttribute("days", days);
        model.addAttribute("formatter", formatter);
        model.addAttribute("commite", conference.getReviewers());

        return "conference";
    }
}
