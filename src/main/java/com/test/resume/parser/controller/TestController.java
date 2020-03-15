package com.test.resume.parser.controller;

import com.test.resume.parser.model.MLResult;
import com.test.resume.parser.model.UploadFileResponse;
import com.test.resume.parser.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin
public class TestController {

    private FileStorageService fileStorageService;

    @Autowired
    public TestController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testEndpoint() {
        fileStorageService.saveTestObject();
        return "Hello World";
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key, @RequestParam(defaultValue = "", required = false) String fromSingleRequest) throws Exception {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = getFileLocationUri(key, fileName);

        if (fromSingleRequest.isEmpty()) {
            fileStorageService.parseResume(key);
        }
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    private String getFileLocationUri(String key, String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .queryParam("key", key)
                .toUriString();
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam("key") String key) {
        List<UploadFileResponse> responseList = getUploadResponseLIst(files, key);
        fileStorageService.parseResume(key);

        return responseList;
    }

    private List<UploadFileResponse> getUploadResponseLIst(MultipartFile[] files, String key) {
        List<UploadFileResponse> responseList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                UploadFileResponse response = uploadFile(file, key, "multiple");
                responseList.add(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseList;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request, @RequestParam("key") String key) throws Exception {
        Resource resource = fileStorageService.loadFileAsResource(fileName, key);
        String contentType = null;
        try {
            contentType = getContentType(request, resource);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return buildResponseEntity(resource, contentType);
    }

    private String getContentType(HttpServletRequest request, Resource resource) throws IOException {
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if (contentType == null) {
            return "application/octet-stream";
        }


        return contentType;
    }

    private ResponseEntity<Resource> buildResponseEntity(Resource resource, String contentType) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/results")
    public MLResult getMachineLearningResults(@RequestParam("timestamp") String timestamp) throws Exception {
        Thread.sleep(4000);
        return fileStorageService.getMachineLearningResultsFromFile(timestamp);

    }
}
