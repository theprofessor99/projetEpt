package com.ept.conference.controller;

import com.ept.conference.controller.dto.UserRegistrationDto;
import com.ept.conference.model.User;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {


    private UserService userService;
    private UserRepository userRepository;

    public LoginController(UserService userService, UserRepository userRepository) {
        super();
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "login";
    }

}
