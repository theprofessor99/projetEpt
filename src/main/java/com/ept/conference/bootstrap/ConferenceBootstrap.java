package com.ept.conference.bootstrap;

import com.ept.conference.model.*;
import com.ept.conference.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ConferenceBootstrap implements CommandLineRunner {


    private final ArticleRepository articleRepository;
    private final ConferenceRepository conferenceRepository;
    private final SessionRepository sessionRepository;
    private final ThemeRepository themeRepository;
    private final TutorialRepository tutorialRepository;
    private final UserRepository userRepository;

    public ConferenceBootstrap(ArticleRepository articleRepository, ConferenceRepository conferenceRepository, SessionRepository sessionRepository, ThemeRepository themeRepository, TutorialRepository tutorialRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.conferenceRepository = conferenceRepository;
        this.sessionRepository = sessionRepository;
        this.themeRepository = themeRepository;
        this.tutorialRepository = tutorialRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User oussama = new User("oussama", "oussamamaster0@gmail.com", "password");
        User houssem = new User("houssem", "houssemmaster0@gmail.com", "password");
        User rami = new User("rami", "ramimaster0@gmail.com", "password");
        User kammoun = new User("kammoun", "kammounmaster0@gmail.com", "password");

        Article article1 = new Article("article1", "hfdjmqf");
        article1.getReviewers().add(houssem);
        houssem.getToReviewArticles().add(article1);

        article1.getReviewers().add(rami);
        rami.getToReviewArticles().add(article1);

        Article article2 = new Article("article2", "hfdjmqf");
        article2.getReviewers().add(kammoun);
        kammoun.getToReviewArticles().add(article2);

        Session session1 = new Session(LocalDateTime.now());

        Tutorial tutorial1 = new Tutorial("tutorial1", "dmqmkfjmq");

        Conference conference1 = new Conference("conf1", LocalDateTime.now(), "hfmdjfq");


        session1.getArticles().add(article1);
        article1.setSession(session1);

        conference1.getSessions().add(session1);
        session1.setConference(conference1);

        conference1.getTutorials().add(tutorial1);
        tutorial1.setConference(conference1);

        userRepository.save(oussama);
        userRepository.save(houssem);
        userRepository.save(rami);
        userRepository.save(kammoun);

        articleRepository.save(article1);
        articleRepository.save(article2);

        conference1.setAdmin(oussama);
        oussama.getConferences().add(conference1);

        conferenceRepository.save(conference1);

        sessionRepository.save(session1);

        tutorialRepository.save(tutorial1);



        System.out.println("articles " + articleRepository.count());

        conferenceRepository.findAll().forEach(conference -> System.out.println(conference.getTitle()));

    }
}
