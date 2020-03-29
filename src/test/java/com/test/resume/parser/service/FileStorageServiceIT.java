package com.test.resume.parser.service;


import com.test.resume.parser.FileStorageService;
import com.test.resume.parser.HackTestApplication;
import com.test.resume.parser.entity.ResumeEvent;
import com.test.resume.parser.model.ResumeInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest( webEnvironment = WebEnvironment.MOCK,
        classes = HackTestApplication.class)
@AutoConfigureMockMvc
public class FileStorageServiceIT {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    void storeFile() throws Exception {
        // method setup
        MultipartFile testFile = new MockMultipartFile("test", "test file", "", (byte[]) null);
        ResumeEvent event = new ResumeEvent();
        event.setEventId(123123);

        String response = fileStorageService.storeFile(testFile);

        assertEquals("testing file name", "123123", response);
    }


//    @Test
//    public void testResumeResource() throws Exception {
//
//        MultipartFile testFile = new MockMultipartFile("test", "test file", "", new byte[]{1,2,3,4});
//
//        String eventId = fileStorageService.storeFile(testFile); // eventId - 1
//
//        List<ResumeInfo> resumeInfos = fileStorageService.fetchResumeInformation(Long.parseLong(eventId));
//
//        ResumeInfo resumeInfo = resumeInfos.get(0);
//
//        Resource resource = fileStorageService.loadFileAsResource(resumeInfo.getFileName(), Long.parseLong(eventId), Long.parseLong(resumeInfo.getResumeId()));
//
//        assertNotNull(resource);
//
//
//    }
}
