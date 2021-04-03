package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Service
public class ConfernceRegistrationService {

    public final UserRepository userRepository;
    public final ConferenceRepository conferenceRepository;

    public ConfernceRegistrationService(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public ArrayList<String>[] createConference(String title, LocalDateTime date, String description, String[] commitee, User admin){
        ArrayList<User> users = new ArrayList<>();
        ArrayList<String> notFounduUsers = new ArrayList<>();
        ArrayList<String> foundUsers = new ArrayList<>();
        for(String username : commitee){
            User user = userRepository.findByUsername(username);
            if(user != null){
                users.add(user);
                foundUsers.add(username);
            }else{
                notFounduUsers.add(username);
            }
        }

        if(notFounduUsers.size() != 0 ){
            return new ArrayList[]{foundUsers, notFounduUsers};
        }else{

            Conference conference = new Conference(title, date, description);

            conference.setAdmin(admin);
            conference.setReviewers((Set<User>)users);
            admin.getConferences().add(conference);

            userRepository.save(admin);

            for(User user : users){
                user.getSupervisedConferences().add(conference);
                userRepository.save(user);
            }

            conferenceRepository.save(conference);

            return new ArrayList[]{foundUsers, notFounduUsers};
        }

    }
}
