package com.test.resume.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.entity.Resume;
import com.test.resume.parser.entity.ResumeEvent;
import com.test.resume.parser.model.MLResult;
import com.test.resume.parser.model.Test;
import com.test.resume.parser.repository.ResumeEventRepository;
import com.test.resume.parser.util.FileStorageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {
    private FileStorageProperties properties;
    private FileStorageHelper helper;
    private ResumeEventRepository resumeEventRepository;
    private String basePath;

    @Autowired
    public FileStorageService(FileStorageProperties properties, FileStorageHelper helper, ResumeEventRepository resumeEventRepository) {
        this.properties = properties;
        this.helper = helper;
        this.resumeEventRepository = resumeEventRepository;
        this.basePath = System.getProperty("user.dir") + "/src/main";
    }

    public String storeFile(MultipartFile file) throws Exception {
        // Normalize file name
        String originalFilename = getOriginalFilename(file);
        ResumeEvent event = createNewEventWithResume(file, originalFilename);
        ResumeEvent savedEvent = resumeEventRepository.saveAndFlush(event);

        return String.valueOf(savedEvent.getEventId());
    }

    private ResumeEvent createNewEventWithResume(MultipartFile file, String originalFilename) throws IOException {
        Resume resume = createNewResume(file, originalFilename);

        // create a new resume event object
        ResumeEvent event = new ResumeEvent();
        event.setEventName("new event");
        event.addResume(resume);

        return event;
    }

    private Resume createNewResume(MultipartFile file, String originalFilename) throws IOException {
        // create a new resume, and set resume to event
        Resume resume = new Resume();
        resume.setResumeName(originalFilename);
        resume.setResumeFile(file.getBytes());
        resume.setFavorite(true);
        return resume;
    }

    private String getOriginalFilename(MultipartFile file) throws Exception {
        if (file.getOriginalFilename() == null) throw new Exception("Sorry! Original file name does not exist " + file);
        return file.getOriginalFilename();
    }

    private Path constructTargetPath(String key, String fileName) {
        String uploadDir = basePath + properties.getUploadDir();
        Path filePath = helper.getFilePath(key, uploadDir);
        return filePath.resolve(fileName);
    }

    public Resource loadFileAsResource(String fileName, Long eventId, Long resumeId) throws Exception {


            Optional<ResumeEvent> optionalResumeEvent = resumeEventRepository.findById(eventId);

            ResumeEvent resumeEvent = optionalResumeEvent.orElseThrow(() -> new IllegalStateException(String.format("Resume event with id: s% does not exist", eventId)));

            Resume resume = resumeEvent.getResumeList()
                    .stream()
                    .filter(res -> resumeId == res.getResumeId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("Resume with id: s% does not exist", resumeId)));

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(resume.getResumeFile());

            InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);

            if (inputStreamResource.exists()) {
                return inputStreamResource;
            } else {
                throw new Exception("File not found " + fileName);
            }
    }

    private URI generateURI(String fileName, String key) {
        String fullPath = this.basePath + this.properties.getUploadDir();
        Path filePath = helper.getFilePath(key, fullPath);
        Path normalizedFilePath = filePath.resolve(fileName).normalize();
        return normalizedFilePath.toUri();
    }

    public String parseResume(String key) {
        try {
            Process p = Runtime.getRuntime().exec("/usr/local/bin/python3 " + this.properties.getPythonHome() + " " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public MLResult getMachineLearningResultsFromFile(String timestamp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String filePath = helper.getFilePath(timestamp + ",val", basePath + this.properties.getOutputDir()).toString() + "/output.json";
        File src = new File(filePath);
        return mapper.readValue(src, MLResult.class);
    }

    public void saveTestObject() {
        Test test = new Test();
        test.setUsername(UUID.randomUUID().toString());
        test.setPassword(UUID.randomUUID().toString());
    }
}
