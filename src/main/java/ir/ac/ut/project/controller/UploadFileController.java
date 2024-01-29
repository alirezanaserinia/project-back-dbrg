package ir.ac.ut.project.controller;

import ir.ac.ut.project.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/uploadfile")
public class UploadFileController {
    private StorageService storageService;

    public UploadFileController(StorageService storageService){
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<?> handleFileUpload(@RequestAttribute("file") MultipartFile file) {
        storageService.store(file);

        return ResponseEntity.ok("You successfully uploaded " +
                file.getOriginalFilename() + "!");
    }

    @PostMapping("/multi")
    public ResponseEntity<?> handleMultipleFilesUpload(@RequestAttribute("files") MultipartFile[] files) {
        String message;
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                storageService.store(file);
                fileNames.add(file.getOriginalFilename());
            });

            message = "Uploaded the files successfully: " + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}