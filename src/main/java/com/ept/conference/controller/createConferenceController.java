package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.ConfernceRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/createConf")
public class createConferenceController {

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    public createConferenceController(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }

    @PostMapping
    public String createConference(
            @RequestParam("title") String title,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("description") String description,
            @RequestParam("user") String[] users,
            RedirectAttributes redirectAttributes,
            Principal principal,
            Model model){

        String adminName = principal.getName();
        User admin = userRepository.findByUsername(adminName);
        ConfernceRegistrationService confernceRegistrationService = new ConfernceRegistrationService(userRepository, conferenceRepository);

        ArrayList<String>[] commitee = confernceRegistrationService.createConference(title, date, description, users, admin);

        ArrayList<String> foundUsers = commitee[0];
        ArrayList<String> notFoundUsers = commitee[1];

        if(notFoundUsers.size() == 0 ){
            return "redirect:/index?success";
        }else{
            redirectAttributes.addAttribute("notFoundUsers", notFoundUsers);
            redirectAttributes.addAttribute("foundUsers", foundUsers);

            return "redirect:/index?failure";
        }

    }

}
