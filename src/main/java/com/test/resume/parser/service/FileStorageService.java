package com.test.resume.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.model.MLResult;
import com.test.resume.parser.model.Test;
import com.test.resume.parser.repository.TestRepository;
import com.test.resume.parser.util.FileStorageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageService {
    private FileStorageProperties properties;
    private FileStorageHelper helper;
    private TestRepository testRepository;
    private String basePath;

    @Autowired
    public FileStorageService(FileStorageProperties properties, FileStorageHelper helper, TestRepository testRepository) {
        this.properties = properties;
        this.helper = helper;
        this.testRepository = testRepository;
        this.basePath = System.getProperty("user.dir") + "/src/main";
    }

    public String storeFile(MultipartFile file, String key) throws Exception {
        // Normalize file name
        String originalFilename = getOriginalFilename(file);
        String fileName = StringUtils.cleanPath(originalFilename);
        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
        }
        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = constructTargetPath(key, fileName);
        helper.saveFileOnDisk(file, targetLocation);

        return fileName;
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

    public Resource loadFileAsResource(String fileName, String key) throws Exception {
        try {
            URI uri = generateURI(fileName, key);
            Resource resource = new UrlResource(uri);
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
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
        testRepository.saveAndFlush(test);
    }
}
