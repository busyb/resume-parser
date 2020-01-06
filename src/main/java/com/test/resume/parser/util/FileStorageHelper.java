package com.test.resume.parser.util;

        import org.springframework.stereotype.Component;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.nio.file.StandardCopyOption;

        @Component
public class FileStorageHelper {
    public void saveFileOnDisk(MultipartFile file, Path targetLocation) throws IOException {
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    public Path getFilePath(String key, String basePath) {
        String uniqueDirectory = parseKeyForDirectory(key);
        Path fileStorageLocation = Paths.get(basePath + uniqueDirectory)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            try {
                throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileStorageLocation;
    }

    private String parseKeyForDirectory(String key) {
        String directory = key.split(",")[0];
        return "/" +  directory + "/";
    }
}