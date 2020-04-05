package com.test.resume.parser.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="resume_event")
public class ResumeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @Column(name = "event_name")
    private String eventName;

    @OneToMany( targetEntity=Resume.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<Resume> resumeList;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_create_at")
    Date eventCreateDate;

    public ResumeEvent() {
        this.resumeList = new ArrayList<>();
        this.eventCreateDate = new Timestamp(Instant.now().toEpochMilli());
    }

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

    public Date getEventCreateDate() {
        return eventCreateDate;
    }

    public void setEventCreateDate(Date eventCreateDate) {
        this.eventCreateDate = eventCreateDate;
    }

    public void addResume(Resume resume) {
        resumeList.add(resume);
    }

    public List<Resume> getResumeList() {
        return resumeList;
    }
}
