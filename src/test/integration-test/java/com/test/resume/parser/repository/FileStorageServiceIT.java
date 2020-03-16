package com.test.resume.parser.repository;


import com.test.resume.parser.entity.Resume;
import com.test.resume.parser.model.ResumeInfo;
import com.test.resume.parser.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class FileStorageServiceIT {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    private void testResumeResource() throws Exception {

        MultipartFile testFile = new MockMultipartFile("test", "test file", "", new byte[]{1,2,3,4});

        String eventId = fileStorageService.storeFile(testFile); // eventId - 1

        List<ResumeInfo> resumeInfos = fileStorageService.fetchResumeInformation(Long.parseLong(eventId));

        ResumeInfo resumeInfo = resumeInfos.get(0);

        Resource resource = fileStorageService.loadFileAsResource(resumeInfo.getFileName(), Long.parseLong(eventId), Long.parseLong(resumeInfo.getResumeId()));

        assertNotNull(resource);


    }

}
