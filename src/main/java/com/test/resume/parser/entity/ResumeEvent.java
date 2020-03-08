package com.test.resume.parser.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="resume_event")
public class ResumeEvent {

    @Id
    @GeneratedValue
    long eventId;

    @Column(name = "event_name")
    String eventName;

    @OneToMany( targetEntity=Resume.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<Resume> resumeList;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_create_at")
    Date eventCreateDate;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<Resume> getResumeList() {
        return resumeList;
    }

    public void setResumeList(List<Resume> resumeList) {
        this.resumeList = resumeList;
    }

    public Date getEventCreateDate() {
        return eventCreateDate;
    }

    public void setEventCreateDate(Date eventCreateDate) {
        this.eventCreateDate = eventCreateDate;
    }
}
