package com.test.resume.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.model.MLResult;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {


//    private final Path fileStorageLocation;
    private FileStorageProperties properties;
    private FileStorageHelper helper;

    @Autowired
    public FileStorageService(FileStorageProperties properties, FileStorageHelper helper) {
        this.properties = properties;
        this.helper = helper;
    }

    public String storeFile(MultipartFile file, String key) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                try {
                    throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Copy file to the target location (Replacing existing file with the same name)
            String uploadDir = properties.getUploadDir();
            Path targetLocation = helper.getFilePath(key, uploadDir).resolve(fileName);
            helper.saveFileOnDisk(file, targetLocation);
        } catch (IOException ex) {
            try {
                throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    public Resource loadFileAsResource(String fileName, String key) throws Exception {
        try {
            Path filePath = helper.getFilePath(key, this.properties.getUploadDir()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
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
        String filePath = helper.getFilePath(timestamp + ",val", this.properties.getOutputDir()).toString() + "/output.json";
        File src = new File(filePath);
        return mapper.readValue(src, MLResult.class);
    }
}
