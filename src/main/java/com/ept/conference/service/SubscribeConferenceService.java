package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscribeConferenceService {

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    public SubscribeConferenceService(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public void subscribe(User user, Conference conference){
        user.getSubscribedConferences().add(conference);
        conference.getParticipants().add(user);

        userRepository.save(user);
        conferenceRepository.save(conference);
    }
}
