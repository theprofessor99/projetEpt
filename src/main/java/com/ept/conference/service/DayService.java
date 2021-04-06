package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Session;
import com.ept.conference.model.Tutorial;

import java.time.LocalDate;
import java.util.*;

public class DayService {

    private LocalDate date = LocalDate.now();
    private Session[] sessions = new Session[4];
    //private ArrayList<Session> sessions = new ArrayList<>();
    private Tutorial tutorial;

    public DayService() {
    }

    public DayService(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Session[] getSessions() {
        return sessions;
    }

    public void setSessions(Session[] sessions) {
        this.sessions = sessions;
    }

    /*public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }*/

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    static class SortByDate implements Comparator<Session>
    {
        public int compare(Session a, Session b)
        {
            return a.getDate().isAfter(b.getDate()) ? 1 : -1;
        }
    }

    static class SortTutorialByDate implements Comparator<Tutorial>
    {
        public int compare(Tutorial a, Tutorial b)
        {
            return a.getDate().isAfter(b.getDate()) ? 1 : -1;
        }
    }


    public static ArrayList<DayService> getDays(Conference conference){

        ArrayList<DayService> days = new ArrayList<>();
        ArrayList<DayService> bufferDays = new ArrayList<>();
        ArrayList<Session> confSessions = new ArrayList<>(conference.getSessions());

        Collections.sort(confSessions, new SortByDate());

        ArrayList<Tutorial> confTutorials = new ArrayList<>(conference.getTutorials());
        Collections.sort(confTutorials, new SortTutorialByDate());

        for(Tutorial tuto : confTutorials){
            DayService day = new DayService(LocalDate.from(tuto.getDate()));
            day.setTutorial(tuto);
            days.add(day);
        }
        int k = 0;
        DayService day = new DayService(conference.getDate());
        for(Session session : confSessions){

            day.getSessions()[0] = session;

            if(k == 3){
                k = 0;
                bufferDays.add(day);
                day = new DayService(LocalDate.from(session.getDate()));
            }else {
                k++;
            }
        }
        if(k != 3){ bufferDays.add(day);}

        if(bufferDays.size() < days.size()){
            for(int i = 0; i < bufferDays.size(); i++){
                days.get(i).setSessions(bufferDays.get(i).getSessions());
            }
            return days;
        }else{
            for(int i = 0; i < days.size(); i++){
                bufferDays.get(i).setSessions(days.get(i).getSessions());
            }
            return bufferDays;
        }
    }

}
