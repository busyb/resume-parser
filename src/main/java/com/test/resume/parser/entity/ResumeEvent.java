package com.test.resume.parser.entity;

import javax.persistence.*;
import java.util.List;

public class ResumeEvent {

    @Id
    @GeneratedValue
    int eventId;

    @Column(name = "event_name")
    String eventName;

    @OneToMany( targetEntity=Resume.class )
    private List resumeList;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_create_at")
    String eventCreateDate;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCreateDate() {
        return eventCreateDate;
    }

    public void setEventCreateDate(String eventCreateDate) {
        this.eventCreateDate = eventCreateDate;
    }

    public List getResumeList() {
        return resumeList;
    }

    public void setResumeList(List resumeList) {
        this.resumeList = resumeList;
    }
}
