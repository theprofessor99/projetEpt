package com.ept.conference.service;

import com.ept.conference.model.*;
import com.ept.conference.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Service
public class RateArticleService {

    private final RateArticleRepository rateArticleRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private  final ConferenceRepository conferenceRepository;

    public RateArticleService(RateArticleRepository rateArticleRepository, ArticleRepository articleRepository, UserRepository userRepository, SessionRepository sessionRepository, ConferenceRepository conferenceRepository) {
        this.rateArticleRepository = rateArticleRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.conferenceRepository = conferenceRepository;
    }

    public float rateArticle(Article article, User user, float rating){
        ArrayList<RateArticle> ratings = rateArticleRepository.findRateArticleByArticle(article);

        int nbRatings = ratings.size();

        float newRating = (article.getRating() * nbRatings + rating) / (nbRatings + 1);

        if (nbRatings + 1 == article.getReviewers().size()){

            if(newRating < 1.5){
                article.getConference().getArticles().remove(article);
                user.getToReviewArticles().remove(article);

                articleRepository.delete(article);
                //todo add code to notify the user
            }else{

                LocalDateTime newTime = article.getConference().getDate().atTime(7,30);

                for(Session session : sessionRepository.findSessionByConference(article.getConference())){
                    Set<Theme> intersection = article.getThemes();
                    intersection.retainAll(session.getThemes());
                    if (intersection.size() != 0 && session.getArticles().size() < 3){
                        session.setThemes(intersection);
                        session.getArticles().add(article);
                        article.setSession(session);
                        articleRepository.save(article);
                        return newRating;
                    }

                    newTime = newTime.isBefore(session.getDate()) ? session.getDate() : newTime;
                }


                if(newTime.getHour() == 10){
                    newTime = newTime.plusHours(3);
                    newTime = newTime.plusMinutes(30);
                }else if(newTime.getHour() == 17){
                    newTime = newTime.plusHours(16);
                }else{
                    newTime = newTime.plusMinutes(90);
                }

                Session session = new Session(newTime, article.getConference());
                article.getConference().getSessions().add(session);
                session.setThemes(article.getThemes());
                article.setRating(newRating);

                session.getArticles().add(article);
                article.setSession(session);
                articleRepository.save(article);

            }
        }else {
            RateArticle rateArticle = new RateArticle(rating);
            rateArticle.setArticle(article);
            rateArticle.setReviewer(user);

            article.setRating(newRating);
            user.getRateArticles().add(rateArticle);
            article.getArticles().add(rateArticle);
            rateArticleRepository.save(rateArticle);
        }

        article.getReviewers().remove(user);
        user.getToReviewArticles().remove(article);
        articleRepository.save(article);

        return newRating;
    }
}
