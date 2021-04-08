package com.ept.conference.controller;

import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.ConfernceRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/createConf")
public class createConferenceController {

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    public createConferenceController(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }
    //Todo modify create conference js file to eliminate the possibility of two duplicate usernames
    //Todo solve the problem of not sending any usernames

    /*@ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex,
                                      @RequestParam("title") String title,
                                      @RequestParam("date") String date,
                                      @RequestParam("description") String description,
                                      Model model,
                                      Principal principal) {


        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        String username = principal.getName();
        model.addAttribute("username", username);
        addAttributes(title, date, description, principal, model, new ArrayList<String>(), new ArrayList<String>());
        model.addAttribute("error", "you need at least three commitee members");

        return "index";
    }*/

    @PostMapping
    public String createConference(
            @RequestParam("title") String title,
            @RequestParam("date") String date,
            @RequestParam("description") String description,
            @RequestParam("user") Optional<String[]> us,
            RedirectAttributes redirectAttributes,
            Principal principal,
            Model model){

        String[] users;
        if(!us.isPresent()){
            addAttributes(title, date, description, principal, model, new ArrayList<String>(), new ArrayList<String>());
            model.addAttribute("error", "one or more commitee members not found");
            return "myConferences";
        }else {
            users = us.get();
        }

        String adminEmail = principal.getName();
        User admin = userRepository.findByEmail(adminEmail);

        ConfernceRegistrationService confernceRegistrationService = new ConfernceRegistrationService(userRepository, conferenceRepository);

        ArrayList[] commitee = new ArrayList[0];
        try {
            commitee = confernceRegistrationService.createConference(title, date, description, users, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> foundUsers = commitee[0];
        ArrayList<String> notFoundUsers = commitee[1];

        if(notFoundUsers.size() == 0 && foundUsers.size() >= 3){
            return "redirect:/?success";
        }else if(notFoundUsers.size() != 0){
            addAttributes(title, date, description, principal, model, foundUsers, notFoundUsers);
            model.addAttribute("error", "one or more commitee members not found");


            return "myConferences";
        }else{
            addAttributes(title, date, description, principal, model, foundUsers, notFoundUsers);
            model.addAttribute("error", "you need at least three commitee members");

            return "myConferences";
        }

    }

    private void addAttributes(@RequestParam("title") String title, @RequestParam("date") String date, @RequestParam("description") String description, Principal principal, Model model, ArrayList<String> foundUsers, ArrayList<String> notFoundUsers) {
        model.addAttribute("notFoundUsers", notFoundUsers);
        model.addAttribute("foundUsers", foundUsers);
        String email = principal.getName();

        User user = userRepository.findByEmail(email);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("myConfs", conferenceRepository.findConferenceByAdmin(email));
        model.addAttribute("subscribedConfs", conferenceRepository.findConferenceByParticipant(email));
        model.addAttribute("browseConfs", conferenceRepository.findAll());
        model.addAttribute("title", title);
        model.addAttribute("date", date);
        model.addAttribute("description", description);
        model.addAttribute("fail", true);
    }

}
