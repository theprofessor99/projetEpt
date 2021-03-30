package com.ept.conference.repositories;

import com.ept.conference.model.Conference;
import org.springframework.data.repository.CrudRepository;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {
}
