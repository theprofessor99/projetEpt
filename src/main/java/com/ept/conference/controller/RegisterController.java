package com.ept.conference.controller;

import com.ept.conference.controller.dto.UserRegistrationDto;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserServiceImp userService;
    private final UserRepository userRepository;

    public RegisterController(UserServiceImp userService, UserRepository userRepository) {
        super();
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {
        if(userRepository.findByUsername(registrationDto.getUsername()) != null){
            model.addAttribute("error", "Username already used");
            model.addAttribute("failure", true);
        }else if (userRepository.findByEmail(registrationDto.getEmail()) != null){
            model.addAttribute("error", "Email already used");
            model.addAttribute("failure", true);
        }else{
            userService.save(registrationDto);
            model.addAttribute("success", true);
        }
        return "login";
    }

}
