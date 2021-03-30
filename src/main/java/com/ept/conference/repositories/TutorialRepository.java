package com.ept.conference.repositories;

import com.ept.conference.model.Tutorial;
import org.springframework.data.repository.CrudRepository;

public interface TutorialRepository extends CrudRepository<Tutorial, Long> {
}
