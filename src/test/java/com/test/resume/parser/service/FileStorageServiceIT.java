package com.test.resume.parser.service;


import com.test.resume.parser.FileStorageService;
import com.test.resume.parser.model.ResumeInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceIT {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testResumeResource() throws Exception {

        MultipartFile testFile = new MockMultipartFile("test", "test file", "", new byte[]{1,2,3,4});

        String eventId = fileStorageService.storeFile(testFile); // eventId - 1

        List<ResumeInfo> resumeInfos = fileStorageService.fetchResumeInformation(Long.parseLong(eventId));

        ResumeInfo resumeInfo = resumeInfos.get(0);

        Resource resource = fileStorageService.loadFileAsResource(resumeInfo.getFileName(), Long.parseLong(eventId), Long.parseLong(resumeInfo.getResumeId()));

        assertNotNull(resource);


    }

}
