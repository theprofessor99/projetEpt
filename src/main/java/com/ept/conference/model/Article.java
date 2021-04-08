package com.ept.conference.model;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Article {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private float rating;
    private String file;

    @ManyToMany(mappedBy = "articles")
    private Set<Theme> themes = new HashSet<Theme>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Session session;

    @ManyToMany
    @JoinTable(name = "article_author", joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<User> authors = new HashSet<User>();

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "article_id")
    private Set<RateArticle> articles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "article_reviewer", joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "reviewer_id"))
    private Set<User> reviewers = new HashSet<User>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Conference conference;

    @ManyToMany
    @JoinTable(name = "article_presenters", joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "presenter_id"))
    private Set<User> presenters = new HashSet<User>();

    public Article() {
    }

    public Article(String title, String description) {
        this.title = title;
        this.description = description;
        this.rating = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<User> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<User> authors) {
        this.authors = authors;
    }

    public Set<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<User> reviewers) {
        this.reviewers = reviewers;
    }

    public Set<RateArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<RateArticle> articles) {
        this.articles = articles;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Set<User> getPresenters() {
        return presenters;
    }

    public void setPresenters(Set<User> presenters) {
        this.presenters = presenters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return id != null ? id.equals(article.id) : article.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}
