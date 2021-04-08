package com.ept.conference.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;

    @ManyToMany(mappedBy = "sessions")
    private Set<Theme> themes = new HashSet<Theme>();

    @ManyToOne(cascade = CascadeType.MERGE)
    private Conference conference;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "session_id")
    private Set<Article> articles = new HashSet<Article>();

    public Session() {
    }

    public Session(LocalDateTime date, Conference conference) {
        this.date = date;
        this.conference = conference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<Theme> getThemes() {
        return themes;
    }

    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        return id != null ? id.equals(session.id) : session.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
