package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Tutorial;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.TutorialRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class AddTutorialService {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final TutorialRepository tutorialRepository;

    public AddTutorialService(ConferenceRepository conferenceRepository, UserRepository userRepository, TutorialRepository tutorialRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.tutorialRepository = tutorialRepository;
    }

    static class SortTutorialByDate implements Comparator<Tutorial>
    {
        public int compare(Tutorial a, Tutorial b)
        {
            return a.getDate().isAfter(b.getDate()) ? 1 : -1;
        }
    }

    public void createTuto(String title, String description, User tutor, Conference conference){
        Tutorial tuto = new Tutorial(title, description);
        tutorialRepository.save(tuto);

        LocalDateTime date = conference.getTutorials().stream().max(new SortTutorialByDate()).get().getDate().plusDays(1);

        tuto.setDate(date);

        tuto.setTutor(tutor);
        tutor.getTutorial().add(tuto);
        tuto.setConference(conference);
        conference.getTutorials().add(tuto);

        tutorialRepository.save(tuto);

    }

}
