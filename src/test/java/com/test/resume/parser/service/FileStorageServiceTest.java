package com.test.resume.parser.service;

import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.util.FileStorageHelper;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class FileStorageServiceTest {

    private FileStorageService service;
    private FileStorageProperties properties;
    private FileStorageHelper helper;


    @Test
    void storeFile() throws IOException {
        // general setup
        properties = mock(FileStorageProperties.class);
        helper = mock(FileStorageHelper.class);
        service = new FileStorageService(properties, helper);

        // method setup
        MultipartFile testFile = new MockMultipartFile("test","test file", "", (byte[]) null);
        String testKey = "test Key";

        when(properties.getUploadDir()).thenReturn("/basePath");
        when(helper.getFilePath(any(), any())).thenReturn(mock(Path.class));
        doNothing().when(helper).saveFileOnDisk(any(), (any()));

        String response = service.storeFile(testFile, testKey);

        assertEquals("testing file name","test file",response);
    }

}