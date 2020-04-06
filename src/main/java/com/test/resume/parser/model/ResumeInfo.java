package com.test.resume.parser.model;

public class ResumeInfo {
    private String resumeId;
    private String fileName;

    public ResumeInfo(String resumeId, String fileName) {
        this.resumeId = resumeId;
        this.fileName = fileName;
    }

    public String getResumeId() {
        return resumeId;
    }

    public void setResumeId(String resumeId) {
        this.resumeId = resumeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
