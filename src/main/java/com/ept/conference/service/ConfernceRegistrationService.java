package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConfernceRegistrationService {

    public final UserRepository userRepository;
    public final ConferenceRepository conferenceRepository;

    public ConfernceRegistrationService(UserRepository userRepository, ConferenceRepository conferenceRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public ArrayList<String>[] createConference(String title, String date, String description, String[] commitee, User admin) throws ParseException {
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

        if(notFounduUsers.size() != 0 || users.size() < 3){
            return new ArrayList[]{foundUsers, notFounduUsers};
        }else{

            Conference conference = new Conference(title, LocalDate.parse(date), description);

            conference.setAdmin(admin);
            conference.setReviewers(new HashSet<>(users));
            admin.getConferences().add(conference);

            userRepository.save(admin);

            for(User user : users){
                user.getSupervisedConferences().add(conference);
                userRepository.save(user);
            }

            return new ArrayList[]{foundUsers, notFounduUsers};
        }

    }
}
