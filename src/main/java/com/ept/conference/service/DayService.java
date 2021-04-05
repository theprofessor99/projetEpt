package com.ept.conference.service;

import com.ept.conference.model.Conference;
import com.ept.conference.model.Session;
import com.ept.conference.model.Tutorial;

import java.util.*;

public class DayService {

    private Date date;
    private Session[] sessions = new Session[4];
    private Tutorial tutorial;

    public DayService() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Session[] getSessions() {
        return sessions;
    }

    public void setSessions(Session[] sessions) {
        this.sessions = sessions;
    }

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
        ArrayList<Session> confSessions = new ArrayList<Session>(conference.getSessions());

        Collections.sort(confSessions, new SortByDate());

        ArrayList<Tutorial> confTutorials = new ArrayList<>(conference.getTutorials());
        Collections.sort(confTutorials, new SortTutorialByDate());

        for(Tutorial tuto : confTutorials){
            DayService day = new DayService();
            day.setTutorial(tuto);
            days.add(day);
        }
        int k = 0;
        DayService day = new DayService();
        for(Session session : confSessions){

            day.getSessions()[k] = session;

            if(k == 3){
                k = 0;
                bufferDays.add(day);
                day = new DayService();
            }else {
                k++;
            }
        }

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
