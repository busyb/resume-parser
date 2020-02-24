package com.test.resume.parser.entity;

import java.util.Date;

public class Resume {
    int resumeId;

    String resumeName;

    byte[] resumeFile;

    Date resumeCreateAt;

    int resumeIsDelete;

    int resumeIsMatch;

    int resumeFavoId;

    int resumeEventId;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
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
}
