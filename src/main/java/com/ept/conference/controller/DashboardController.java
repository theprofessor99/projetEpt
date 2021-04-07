package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class DashboardController {

    public final ConferenceRepository conferenceRepository;
    public final UserRepository userRepository;

    public DashboardController(ConferenceRepository conferenceRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/")
    public String dashboard(Principal principal, Model model){

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("myConfs", conferenceRepository.findConferenceByAdmin(email));
        model.addAttribute("subscribedConfs", conferenceRepository.findConferenceByParticipant(email));
        model.addAttribute("browseConfs", conferenceRepository.findAll());

        return "index";
    }
    @GetMapping("/myConferences")
    public String getMyConfs(Principal principal, Model model){

        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("myConfs", conferenceRepository.findConferenceByAdmin(email));
        model.addAttribute("supervisedConfs", conferenceRepository.findConferenceByReviewer(email));

        return "myConferences";
    }

}
