package com.sumit.multipartdemo.controller;

import com.sumit.multipartdemo.entity.FileUpload;
import com.sumit.multipartdemo.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload/file")
    public ResponseEntity<?> saveFileToLocalController(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("metadata") String metadata) throws MalformedURLException, URISyntaxException {
        String fileId = fileUploadService.saveFileToDatabase(file, metadata);
        String id = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build(fileId).toString();
        return ResponseEntity.status(HttpStatus.CREATED).header("fileId", id).build();
    }

    @GetMapping("/upload/file/{id}")
    public ResponseEntity<Resource> fetchUploadedFile(@PathVariable("id") String fileId) {
        Optional<FileUpload> uploadedFileOptional = fileUploadService.getUploadedFile(fileId);
        return uploadedFileOptional.<ResponseEntity<Resource>>map(fileUpload -> ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileUpload.getFileName())
                .body(new ByteArrayResource(fileUpload.getFileData()))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
