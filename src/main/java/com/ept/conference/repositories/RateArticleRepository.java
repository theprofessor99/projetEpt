package com.ept.conference.repositories;

import com.ept.conference.model.Article;
import com.ept.conference.model.RateArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RateArticleRepository extends CrudRepository<RateArticle, Long> {

    ArrayList<RateArticle> findRateArticleByArticle(Article article);
}
