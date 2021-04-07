package com.ept.conference.controller;

import com.ept.conference.repositories.ConferenceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class SearchController {

    private final ConferenceRepository conferenceRepository;

    public SearchController(ConferenceRepository conferenceRepository) {
        this.conferenceRepository = conferenceRepository;
    }

    @RequestMapping("/search")
    public String findConfs(@RequestParam("name") String name, Principal principal, Model model){

        String username = principal.getName();
        model.addAttribute("username", username);

        model.addAttribute("foundConfs", conferenceRepository.findConferenceByTitle(name));

        return "findConference";
    }
}
