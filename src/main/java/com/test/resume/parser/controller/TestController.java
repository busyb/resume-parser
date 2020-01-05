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

        return "Hello World";
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key, @RequestParam(defaultValue = "", required = false) String fromSingleRequest) {

        System.out.println(file);


        String fileName = fileStorageService.storeFile(file, key);



        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .queryParam("key", key)
                .toUriString();

        if(fromSingleRequest.isEmpty()){
            fileStorageService.parseResume(key);
        }


        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("file") MultipartFile[] files, @RequestParam("key") String key) {
        List<UploadFileResponse> responseList = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, key, "multiple"))
                .collect(Collectors.toList());

        fileStorageService.parseResume(key);

        return responseList;
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request, @RequestParam("key") String key) throws Exception {
        Resource resource = fileStorageService.loadFileAsResource(fileName, key);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/results")
    public MLResult getMachineLearningResults(@RequestParam("timestamp") String timestamp) throws Exception{
        Thread.sleep(4000);
        return fileStorageService.getMachineLearningResultsFromFile(timestamp);

    }
}
