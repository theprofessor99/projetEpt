package com.ept.conference.service;

import com.ept.conference.model.Article;
import com.ept.conference.model.Conference;
import com.ept.conference.model.Theme;
import com.ept.conference.model.User;
import com.ept.conference.repositories.ArticleRepository;
import com.ept.conference.repositories.ConferenceRepository;
import com.ept.conference.repositories.ThemeRepository;
import com.ept.conference.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class CreateArticleService {

    private final ArticleRepository articleRepository;
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    public CreateArticleService(ArticleRepository articleRepository, ConferenceRepository conferenceRepository, UserRepository userRepository, ThemeRepository themeRepository) {
        this.articleRepository = articleRepository;
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.themeRepository = themeRepository;
    }

    public ArrayList<String>[] createArticle(String title, String description, String[] authors, String[] themes, Conference conference){
        ArrayList<User> users = new ArrayList<>();
        ArrayList<String> notFounduUsers = new ArrayList<>();
        ArrayList<String> foundUsers = new ArrayList<>();

        for(String username : authors){
            User user = userRepository.findByUsername(username);
            if(user != null){
                users.add(user);
                foundUsers.add(username);
            }else{
                notFounduUsers.add(username);
            }
        }

        if(notFounduUsers.size() != 0){
            return new ArrayList[]{foundUsers, notFounduUsers};
        }else {

            Article article = new Article(title, description);
            articleRepository.save(article);

            Set<Theme> articleThemes = new HashSet<>();

            for(String theme : themes){
                Theme t = new Theme(theme);
                t.getArticles().add(article);
                themeRepository.save(t);
                articleThemes.add(t);
            }

            article.setThemes(articleThemes);
            article.setAuthors(new HashSet<>(users));
            article.setConference(conference);
            conference.getArticles().add(article);

            for (User user : users) {
                user.getArticles().add(article);
            }

            articleRepository.save(article);

            Set<User> reviewers = conference.getReviewers();

            int k = 0;
            for(User u : reviewers){
                for(String theme : themes){
                    if(u.getSpeciality() != null && u.getSpeciality().contains(theme)) {
                        u.getToReviewArticles().add(article);
                        article.getReviewers().add(u);
                        k++;
                        break;
                    }
                }
            }
            if(k < 3){
                for(User user : reviewers){
                    if(!article.getReviewers().contains(user)){
                        user.getToReviewArticles().add(article);
                        article.getReviewers().add(user);
                        k++;
                    }
                    if(k == 3){
                        break;
                    }
                }
            }

            article.getReviewers().forEach(r -> System.out.println(r.getUsername()));
            userRepository.saveAll(article.getReviewers());
            articleRepository.save(article);

            return new ArrayList[]{foundUsers, notFounduUsers};
        }

    }
}
