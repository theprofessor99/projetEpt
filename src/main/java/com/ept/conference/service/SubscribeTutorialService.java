package com.ept.conference.service;

import com.ept.conference.model.Tutorial;
import com.ept.conference.model.User;
import com.ept.conference.repositories.TutorialRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscribeTutorialService {

    private final UserRepository userRepository;
    private final TutorialRepository tutorialRepository;

    public SubscribeTutorialService(UserRepository userRepository, TutorialRepository tutorialRepository) {
        this.userRepository = userRepository;
        this.tutorialRepository = tutorialRepository;
    }

    public void attendTuto(User user, Tutorial tuto){

        user.getSubscribedTutorial().add(tuto);
        tuto.getParticipants().add(user);

        userRepository.save(user);
        tutorialRepository.save(tuto);

        if(tuto.getParticipants().size() < 10 && LocalDate.now().plusDays(1).isBefore(tuto.getConference().getDate())){
            tuto.setStatus("canceled");
        }

        if(tuto.getParticipants().size() == 10){
            tuto.setStatus("active");
        }


    }
}
