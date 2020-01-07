package com.test.resume.parser.service;

import com.test.resume.parser.config.FileStorageProperties;
import com.test.resume.parser.model.MLResult;
import com.test.resume.parser.model.Result;
import com.test.resume.parser.util.FileStorageHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class FileStorageServiceTest {

    private FileStorageService service;
    private FileStorageProperties properties;
    private FileStorageHelper helper;

    @BeforeEach
    void setup() {
        properties = mock(FileStorageProperties.class);
        helper = mock(FileStorageHelper.class);
        service = new FileStorageService(properties, helper);
    }


    @Test
    void storeFile() throws IOException {
        // method setup
        MultipartFile testFile = new MockMultipartFile("test","test file", "", (byte[]) null);
        String testKey = "test Key";

        when(properties.getUploadDir()).thenReturn("/basePath");
        when(helper.getFilePath(any(), any())).thenReturn(mock(Path.class));
        doNothing().when(helper).saveFileOnDisk(any(), (any()));

        String response = service.storeFile(testFile, testKey);

        assertEquals("testing file name","test file",response);
    }

    @Test
    void getMachineLearningResultsFromFile() throws IOException{
        String timestamp = new Date().toString();
        when(properties.getOutputDir()).thenReturn("");
        File file = new File(getClass().getClassLoader().getResource("test").getFile());
        when(helper.getFilePath(any(), any())).thenReturn(Paths.get(file.getAbsolutePath()));
        MLResult response = service.getMachineLearningResultsFromFile(timestamp);
        List<Result> resList = response.getResult();
        for (int i  = 0; i < resList.size(); i++) {
            assertEquals("name", "test0" + (i + 1), resList.get(i).getName());
            assertEquals("outcome", "test0" + (i + 1), resList.get(i).getOutcome());
        }
//        response.getResult().stream().forEach(result -> {
//            assertEquals("name", "test01", result.getName());
//            assertEquals("outcome", "test0101", result.getOutcome());
//        });
    }

}