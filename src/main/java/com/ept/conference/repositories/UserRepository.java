package com.ept.conference.repositories;

import com.ept.conference.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String Email);
    User findByUsername(String username);

}
