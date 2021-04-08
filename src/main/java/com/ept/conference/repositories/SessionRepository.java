package com.ept.conference.repositories;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SessionRepository extends CrudRepository<Session, Long> {

    ArrayList<Session> findSessionByConference(Conference conference);

}
