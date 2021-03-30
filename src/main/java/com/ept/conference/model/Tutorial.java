package com.ept.conference.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String desc;
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Conference conference;

    @ManyToMany
    @JoinTable(name = "tutorial_participant", joinColumns = @JoinColumn(name = "tutorial_id"),
        inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private Set<User> participants = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private User tutor;

    public Tutorial() {
    }

    public Tutorial(String title, String desc) {
        this.title = title;
        this.desc = desc;
        this.status = "pending";
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutorial tutorial = (Tutorial) o;

        return id != null ? id.equals(tutorial.id) : tutorial.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
