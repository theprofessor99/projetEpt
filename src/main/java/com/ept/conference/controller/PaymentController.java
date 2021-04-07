package com.ept.conference.controller;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Tutorial;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.TutorialRepository;
import com.ept.conference.repositories.UserRepository;
import com.ept.conference.service.StripeService;
import com.ept.conference.service.SubscribeConferenceService;
import com.ept.conference.service.SubscribeTutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PaymentController {
    // Reading the value from the application.properties file
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;
    private final TutorialRepository tutorialRepository;

    public PaymentController(UserRepository userRepository, ConferenceRepository conferenceRepository, TutorialRepository tutorialRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tutorialRepository = tutorialRepository;
    }

    @RequestMapping("/payment/subscribe/{id}")
    public String sub(@PathVariable("id") Long id, Principal principal, Model model) {

        model.addAttribute("amount", 350);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("pay", "sub");
        return "payment";
    }

    @RequestMapping("/payment/attend/{id}")
    public String attend(@PathVariable("id") Long id, Principal principal, Model model) {

        model.addAttribute("amount", 150);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("pay", "tuto");
        return "payment";
    }

    @Autowired
    private StripeService stripeService;

    @RequestMapping(value = "/charge/sub/{id}", method = RequestMethod.POST)
    public String chargeCardOnSub(HttpServletRequest request,
                                  @PathVariable("id") Long id,
                                  Principal principal) throws Exception {

        User user = userRepository.findByEmail(principal.getName());
        Conference conference = conferenceRepository.findById(id).get();

        SubscribeConferenceService service = new SubscribeConferenceService(userRepository, conferenceRepository);
        service.subscribe(user, conference);

        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token, amount);
        return "redirect:/conference/" + id + "?subSuccess";
    }

    @RequestMapping(value = "/charge/attend/{id}", method = RequestMethod.POST)
    public String chargeCardOnAttend(HttpServletRequest request,
                                     @PathVariable("id") Long id,
                                     Principal principal) throws Exception {

        User user = userRepository.findByEmail(principal.getName());
        Tutorial tuto = tutorialRepository.findById(id).get();

        SubscribeTutorialService service = new SubscribeTutorialService(userRepository, tutorialRepository);
        service.attendTuto(user, tuto);

        Long confId = tuto.getConference().getId();
        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token, amount);
        return "redirect:/conference/" + confId + "?tutoSuccess";
    }
}
