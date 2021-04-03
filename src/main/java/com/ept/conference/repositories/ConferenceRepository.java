package com.ept.conference.repositories;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    @Query("SELECT c FROM Conference c WHERE c.admin.username = ?1")
    Collection<Conference> findConferenceByAdmin(String username);

    @Query("SELECT c FROM Conference c JOIN c.participants p WHERE p.username = ?1")
    Collection<Conference> findConferenceByParticipant(String participant);

}
