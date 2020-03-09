package com.test.resume.parser.service;

import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.entity.ResumeEvent;
import com.test.resume.parser.repository.ResumeEventRepository;
import com.test.resume.parser.repository.TestRepository;
import com.test.resume.parser.util.FileStorageHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

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

}