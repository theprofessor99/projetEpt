package com.ept.conference.service;

import com.ept.conference.controller.dto.UserRegistrationDto;
import com.ept.conference.model.Role;
import com.ept.conference.model.User;
import com.ept.conference.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {

        User user;
        if(registrationDto.getSpeciality() != "") {
            user = new User(registrationDto.getUsername(), registrationDto.getEmail(),
                    registrationDto.getSpeciality(), passwordEncoder.encode(registrationDto.getPassword()),
                    Arrays.asList(new Role("ROLE_USER"))
            );
        }else {
            user = new User(registrationDto.getUsername(), registrationDto.getEmail(),
                     passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER"))
            );
        }
        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
