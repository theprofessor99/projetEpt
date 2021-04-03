package com.ept.conference.repositories;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    @Query("SELECT c FROM Conference c WHERE c.admin = ?1")
    Set<Conference> findConferenceByAdmin(User user);

    @Query("SELECT c FROM Conference c JOIN Conference.participants p WHERE p = ?1")
    Set<Conference> findConferenceByParticipant(User participant);

}
