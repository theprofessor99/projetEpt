package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.processing.Generated;
import java.security.Principal;

@Controller
@RequestMapping("/error")
public class ErrorController {

    private final UserRepository userRepository;

    public ErrorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String sendError(Principal principal, Model model){
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("username", user.getUsername());
        return "error";
    }

}
