package com.test.resume.parser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    private String outputDir;
    private String pythonHome;

    public String getPythonHome() {
        return pythonHome;
    }

    public void setPythonHome(String pythonHome) {
        this.pythonHome = pythonHome;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}



