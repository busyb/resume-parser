package com.test.resume.parser.service;


import com.test.resume.parser.FileStorageService;
import com.test.resume.parser.HackTestApplication;
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
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

        String response = fileStorageService.storeFile(testFile);

        assertEquals("testing file name", "1", response);
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

    @Test
    public void testFetchResumeInformation() {
        List<ResumeInfo> response  = fileStorageService.fetchResumeInformation(10001L);
        assertEquals("should be 1 record for now", 1, response.size());
    }

    @Test
    public void testLoadFileAsResource() throws Exception{
        Resource res = fileStorageService.loadFileAsResource("hahahaha", 10001L, 666L);
        assertNotNull(res);
        assertTrue(res.contentLength() > 0L);
    }
}
