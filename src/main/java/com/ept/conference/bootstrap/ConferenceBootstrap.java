package com.ept.conference.bootstrap;

import com.ept.conference.model.*;
import com.ept.conference.repositories.*;
import com.ept.conference.service.DayService;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


@Component
public class ConferenceBootstrap implements CommandLineRunner {


    private final ArticleRepository articleRepository;
    private final ConferenceRepository conferenceRepository;
    private final SessionRepository sessionRepository;
    private final ThemeRepository themeRepository;
    private final TutorialRepository tutorialRepository;
    private final UserRepository userRepository;
    private final RateArticleRepository rateArticleRepository;

    public ConferenceBootstrap(ArticleRepository articleRepository, ConferenceRepository conferenceRepository, SessionRepository sessionRepository, ThemeRepository themeRepository, TutorialRepository tutorialRepository, UserRepository userRepository, RateArticleRepository rateArticleRepository) {
        this.articleRepository = articleRepository;
        this.conferenceRepository = conferenceRepository;
        this.sessionRepository = sessionRepository;
        this.themeRepository = themeRepository;
        this.tutorialRepository = tutorialRepository;
        this.userRepository = userRepository;
        this.rateArticleRepository = rateArticleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        /*User oussama = new User("oussama", "oussamamaster0@gmail.com", "afd",bCryptPasswordEncoder.encode("password"), Arrays.asList(new Role("ROLE_USER")));
        User houssem = new User("houssem", "afd@g.c", "fdd", bCryptPasswordEncoder.encode("password"), Arrays.asList(new Role("ROLE_USER")));
        User rami = new User("rami", "afsdd@g.c", "fdd", bCryptPasswordEncoder.encode("password"), Arrays.asList(new Role("ROLE_USER")));
        User kammoun = new User("kammoun", "afdfd@g.c", "fdd", bCryptPasswordEncoder.encode("1"), Arrays.asList(new Role("ROLE_USER")));



        Article article1 = new Article("article1", "hfdjmqf");
        article1.getReviewers().add(houssem);
        houssem.getToReviewArticles().add(article1);

        article1.getReviewers().add(rami);
        rami.getToReviewArticles().add(article1);

        Article article2 = new Article("article2", "hfdjmqf");
        article2.getReviewers().add(kammoun);
        kammoun.getToReviewArticles().add(article2);

        Conference conference1 = new Conference("conf1", LocalDate.now(), "hfmdjfq");

        Session session1 = new Session(LocalDateTime.now(), conference1);

        Tutorial tutorial1 = new Tutorial("tutorial1", "dmqmkfjmq");

        session1.getArticles().add(article1);
        article1.setSession(session1);

        conference1.getSessions().add(session1);
        session1.setConference(conference1);

        conference1.getTutorials().add(tutorial1);
        tutorial1.setConference(conference1);


        article2.setConference(conference1);
        article1.setConference(conference1);

        conference1.setAdmin(oussama);
        oussama.getConferences().add(conference1);

        userRepository.save(houssem);
        userRepository.save(rami);
        userRepository.save(kammoun);

        userRepository.save(oussama);

        conferenceRepository.save(conference1);

        sessionRepository.save(session1);

        articleRepository.save(article1);
        articleRepository.save(article2);

        tutorialRepository.save(tutorial1);



        System.out.println("articles " + articleRepository.count());

        conferenceRepository.findAll().forEach(conference -> System.out.println(conference.getTitle()));
        */
        /*User oussama = new User("oussama", "oussamamaster0@gmail.com", "afd",bCryptPasswordEncoder.encode("1"), Arrays.asList(new Role("ROLE_USER")));
        userRepository.save(oussama);

        Conference conference1 = new Conference("conf1", LocalDate.now(), "hfmdjfq");
        conference1.setAdmin(oussama);
        oussama.getConferences().add(conference1);
        conferenceRepository.save(conference1);

        User kammoun = new User("kammoun", "afdfd@g.c", "fdd", bCryptPasswordEncoder.encode("1"), Arrays.asList(new Role("ROLE_USER")));
        userRepository.save(kammoun);

        conference1.getReviewers().add(kammoun);
        kammoun.getSupervisedConferences().add(conference1);

        Article article2 = new Article("article2", "hfdjmqf");

        article2.getAuthors().add(oussama);
        oussama.getArticles().add(article2);
        article2.setConference(conference1);
        conference1.getArticles().add(article2);
        article2.getReviewers().add(kammoun);
        kammoun.getToReviewArticles().add(article2);

        articleRepository.save(article2);

        User houssem = new User("houssem", "afd@g.c", bCryptPasswordEncoder.encode("1"), Arrays.asList(new Role("ROLE_USER")));
        userRepository.save(houssem);

        houssem.getSubscribedConferences().add(conference1);
        conference1.getParticipants().add(houssem);


        Tutorial tutorial1 = new Tutorial("tutorial1", "dmqmkfjmq");
        tutorial1.setDate(LocalDateTime.now().plusDays(1));
        Tutorial tutorial2 = new Tutorial("tutorial2", "dmqmkfjmq");
        tutorial2.setDate(LocalDateTime.now().plusDays(2));
        Tutorial tutorial3 = new Tutorial("tutorial3", "dmqmkfjmq");
        tutorial3.setDate(LocalDateTime.now().plusDays(5));
        Tutorial tutorial4 = new Tutorial("tutorial4", "dmqmkfjmq");
        tutorial4.setDate(LocalDateTime.now().plusDays(4));

        conference1.getTutorials().add(tutorial1);
        tutorial1.setConference(conference1);
        tutorial1.setTutor(oussama);
        conference1.getTutorials().add(tutorial2);
        tutorial2.setConference(conference1);
        tutorial2.setTutor(oussama);
        conference1.getTutorials().add(tutorial3);
        tutorial3.setConference(conference1);
        tutorial3.setTutor(oussama);
        conference1.getTutorials().add(tutorial4);
        tutorial4.setConference(conference1);
        tutorial4.setTutor(oussama);

        tutorialRepository.save(tutorial1);
        tutorialRepository.save(tutorial2);
        tutorialRepository.save(tutorial3);
        tutorialRepository.save(tutorial4);

        Session session1 = new Session(LocalDateTime.now().plusDays(1), conference1);

        session1.getArticles().add(article2);
        article2.setSession(session1);

        Session session2 = new Session(LocalDateTime.now().plusDays(1).plusMinutes(10), conference1);

        session2.getArticles().add(article2);
        article2.setSession(session2);

        Session session3 = new Session(LocalDateTime.now().plusDays(1).plusMinutes(20), conference1);

        session3.getArticles().add(article2);
        article2.setSession(session3);

        Session session4 = new Session(LocalDateTime.now().plusDays(1).plusMinutes(30), conference1);

        session4.getArticles().add(article2);
        article2.setSession(session4);

        Session session5 = new Session(LocalDateTime.now().plusDays(2), conference1);

        session5.getArticles().add(article2);
        article2.setSession(session5);

        sessionRepository.save(session1);
        sessionRepository.save(session2);
        sessionRepository.save(session3);
        sessionRepository.save(session4);
        sessionRepository.save(session5);

        conference1.getReviewers().add(oussama);
        oussama.getSupervisedConferences().add(conference1);
        conference1.getReviewers().add(houssem);
        houssem.getSupervisedConferences().add(conference1);

        conferenceRepository.save(conference1);*/

    }
}
