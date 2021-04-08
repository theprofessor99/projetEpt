package com.ept.conference.repositories;

import com.ept.conference.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN a.authors w WHERE w.email = ?1")
    Collection<Article> findArticleByAuthor(String author);

    @Query("SELECT a FROM Article a JOIN a.reviewers w WHERE w.email = ?1")
    Collection<Article> findArticleByReviewer(String reviewer);
}
