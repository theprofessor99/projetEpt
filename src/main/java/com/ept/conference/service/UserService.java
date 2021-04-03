package com.ept.conference.service;

import com.ept.conference.controller.dto.UserRegistrationDto;
import com.ept.conference.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto userRegistrationDto);
}