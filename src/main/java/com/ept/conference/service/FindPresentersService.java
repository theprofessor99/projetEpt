package com.ept.conference.service;

import com.ept.conference.model.User;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class FindPresentersService {

    private final UserRepository userRepository;

    public FindPresentersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<String>[] findPresenters(Optional<String[]> optPresenters){

        ArrayList<String> foundPresenters = new ArrayList<>();
        ArrayList<String> notFoundPresenters = new ArrayList<>();

        if(optPresenters.isPresent()){
            String[] presenters = optPresenters.get();
            for(String presenter : presenters){
                User pres = userRepository.findByUsername(presenter);
                if(pres != null){
                    foundPresenters.add(presenter);
                }else{
                    notFoundPresenters.add(presenter);
                }
            }
        }

        return new ArrayList[]{foundPresenters, notFoundPresenters};
    }

}
