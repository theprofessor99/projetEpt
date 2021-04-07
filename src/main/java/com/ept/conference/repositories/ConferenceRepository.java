package com.ept.conference.repositories;

import com.ept.conference.model.Conference;
import com.ept.conference.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {

    @Query("SELECT c FROM Conference c WHERE c.admin.email = ?1")
    Collection<Conference> findConferenceByAdmin(String email);

    @Query("SELECT c FROM Conference c JOIN c.participants p WHERE p.email = ?1")
    Collection<Conference> findConferenceByParticipant(String participant);

    @Query("SELECT c FROM Conference c JOIN c.reviewers r WHERE r.email = ?1")
    Collection<Conference> findConferenceByReviewer(String reviewer);

    Collection<Conference> findConferenceByTitle(String title);
}
