package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class dashboardController {

    public final ConferenceRepository conferenceRepository;
    public final UserRepository userRepository;

    public dashboardController(ConferenceRepository conferenceRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String dashboard(Principal principal, Model model){

        String username = principal.getName();

         User user = userRepository.findByUsername(username);

         model.addAttribute("username", user.getUsername());
         model.addAttribute("myConfs", conferenceRepository.findConferenceByAdmin(user));
         model.addAttribute("subscribedConfs", conferenceRepository.findConferenceByParticipant(user));
         model.addAttribute("browseConfs", conferenceRepository.findAll());

         return "index";



    }

}
