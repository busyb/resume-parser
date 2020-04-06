package com.test.resume.parser.service;

import com.test.resume.parser.FileStorageService;
import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.entity.ResumeEvent;
import com.test.resume.parser.repository.ResumeEventRepository;
import com.test.resume.parser.util.FileStorageHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
        ResumeEvent resumeEvent = new ResumeEvent();
        resumeEvent.setEventId(1L);
        when(resumeEventRepository.saveAndFlush(any())).thenReturn(resumeEvent);

        String response = service.storeFile(testFile);

        assertNotNull(response);
        assertEquals("1", response);
    }

}