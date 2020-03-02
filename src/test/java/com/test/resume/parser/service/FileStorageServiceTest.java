package com.test.resume.parser.service;

import com.test.resume.parser.config.FileStorageProperties;
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
    private TestRepository testRepository;

    @BeforeEach
    void setup() {
        properties = mock(FileStorageProperties.class);
        helper = mock(FileStorageHelper.class);
        testRepository = mock(TestRepository.class);
        service = new FileStorageService(properties, helper, testRepository);
    }

    @Test
    void storeFile() throws Exception {
        // method setup
        String testKey = "test Key";
        MultipartFile testFile = new MockMultipartFile("test", "test file", "", (byte[]) null);
        when(properties.getUploadDir()).thenReturn("/basePath");
        when(helper.getFilePath(any(), any())).thenReturn(mock(Path.class));
        doNothing().when(helper).saveFileOnDisk(any(), (any()));

        String response = service.storeFile(testFile, testKey);

        assertEquals("testing file name", "test file", response);
    }

    @Test
    void testDBconnection() {
        service.saveTestObject();
        verify(testRepository, times(1))
                .saveAndFlush(any(com.test.resume.parser.model.Test.class));
    }

}