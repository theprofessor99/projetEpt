package com.ept.conference.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String Speciality;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Set<Conference> conferences = new HashSet<Conference>();

    @ManyToMany(mappedBy = "participants")
    private Set<Conference> subscribedConferences = new HashSet<Conference>();

    @ManyToMany(mappedBy = "reviewers")
    private Set<Conference> supervisedConferences = new HashSet<Conference>();

    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles = new HashSet<Article>();
    @ManyToMany(mappedBy = "reviewers")
    private Set<Article> toReviewArticles = new HashSet<Article>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tutorial_id")
    private Set<Tutorial> tutorial = new HashSet<Tutorial>();

    @ManyToMany(mappedBy = "participants")
    private Set<Tutorial> subscribedTutorial = new HashSet<Tutorial>();

    public User() {
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))

    private Collection<Role> roles;

    public User(String username, String email, String speciality, String password, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        Speciality = speciality;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String email, String password, Collection<Role> roles) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(Set<Conference> conferences) {
        this.conferences = conferences;
    }

    public Set<Conference> getSubscribedConferences() {
        return subscribedConferences;
    }

    public void setSubscribedConferences(Set<Conference> subscribedConferences) {
        this.subscribedConferences = subscribedConferences;
    }

    public Set<Conference> getSupervisedConferences() {
        return supervisedConferences;
    }

    public void setSupervisedConferences(Set<Conference> supervisedConferences) {
        this.supervisedConferences = supervisedConferences;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Article> getToReviewArticles() {
        return toReviewArticles;
    }

    public void setToReviewArticles(Set<Article> toReviewArticles) {
        this.toReviewArticles = toReviewArticles;
    }

    public Set<Tutorial> getTutorial() {
        return tutorial;
    }

    public void setTutorial(Set<Tutorial> tutorial) {
        this.tutorial = tutorial;
    }

    public Set<Tutorial> getSubscribedTutorial() {
        return subscribedTutorial;
    }

    public void setSubscribedTutorial(Set<Tutorial> subscribedTutorial) {
        this.subscribedTutorial = subscribedTutorial;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
