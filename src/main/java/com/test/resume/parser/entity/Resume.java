package com.test.resume.parser.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="resume")
public class Resume {

    @Id
    @GeneratedValue
    long resumeId;

    @Column(name="resume_name")
    String resumeName;

    @Lob
    @Column(name="resume_file")
    byte[] resumeFile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resume_create_at")
    Date resumeCreateAt;

    @Column(name = "resume_is_delete")
    int resumeIsDelete;

    @Column(name = "resume_is_match")
    int resumeIsMatch;

    @Column(name = "favorite_id")
    int resumeFavoId;

    @Column(name = "resume_event_id")
    int resumeEventId;

    @Column(name = "favorite_status")
    int favoriteStatus;

    public long getResumeId() {
        return resumeId;
    }

    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public byte[] getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(byte[] resumeFile) {
        this.resumeFile = resumeFile;
    }

    public Date getResumeCreateAt() {
        return resumeCreateAt;
    }

    public void setResumeCreateAt(Date resumeCreateAt) {
        this.resumeCreateAt = resumeCreateAt;
    }

    public int getResumeIsDelete() {
        return resumeIsDelete;
    }

    public void setResumeIsDelete(int resumeIsDelete) {
        this.resumeIsDelete = resumeIsDelete;
    }

    public int getResumeIsMatch() {
        return resumeIsMatch;
    }

    public void setResumeIsMatch(int resumeIsMatch) {
        this.resumeIsMatch = resumeIsMatch;
    }

    public int getResumeFavoId() {
        return resumeFavoId;
    }

    public void setResumeFavoId(int resumeFavoId) {
        this.resumeFavoId = resumeFavoId;
    }

    public int getResumeEventId() {
        return resumeEventId;
    }

    public void setResumeEventId(int resumeEventId) {
        this.resumeEventId = resumeEventId;
    }

    public int getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(int favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }
}
