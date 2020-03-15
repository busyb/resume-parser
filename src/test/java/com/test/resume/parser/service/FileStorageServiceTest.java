package com.test.resume.parser.service;

import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.entity.Resume;
import com.test.resume.parser.entity.ResumeEvent;
import com.test.resume.parser.repository.ResumeEventRepository;
import com.test.resume.parser.repository.TestRepository;
import com.test.resume.parser.util.FileStorageHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

class FileStorageServiceTest {

    private FileStorageService service;
    private FileStorageProperties properties;
    private FileStorageHelper helper;
    private ResumeEventRepository resumeEventRepository;

    @BeforeEach
    void setup() {
        properties = mock(FileStorageProperties.class);
        helper = mock(FileStorageHelper.class);
        resumeEventRepository = mock(ResumeEventRepository.class);
        service = new FileStorageService(properties, helper, resumeEventRepository);
    }

    @Test
    void storeFile() throws Exception {
        // method setup
        MultipartFile testFile = new MockMultipartFile("test", "test file", "", (byte[]) null);
        ResumeEvent event = new ResumeEvent();
        event.setEventId(123123);
        when(resumeEventRepository.saveAndFlush(any())).thenReturn(event);

        String response = service.storeFile(testFile);

        assertEquals("testing file name", "123123", response);
    }

    @Test
    void loadFileAsResource() throws Exception{
        String fileName = "test123";
        Long eventId = 123123L;
        Long resumeId = 111L;

        ResumeEvent event = new ResumeEvent();
        event.setEventId(eventId);
        Resume resume = new Resume();
        resume.setResumeId(resumeId);
        resume.setResumeFile(new byte[]{1,2,3,4});
        event.getResumeList().add(resume);
        when(resumeEventRepository.findById(any())).thenReturn(Optional.of(event));

        Resource response = service.loadFileAsResource(fileName, eventId, resumeId);
        assertNotNull("not null!!!", response);
//        assertEquals("test event and resume", String.valueOf(new byte[]{1,2,3,4}), response.getFilename());
    }

}