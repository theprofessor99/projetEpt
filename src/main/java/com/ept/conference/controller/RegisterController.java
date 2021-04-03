package com.ept.conference.controller;

import com.ept.conference.controller.dto.UserRegistrationDto;
import com.ept.conference.service.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserServiceImp userService;

    public RegisterController(UserServiceImp userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.save(registrationDto);
        //return "error";
        return "redirect:/login?success";
    }

}
