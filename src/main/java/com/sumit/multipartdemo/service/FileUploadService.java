package com.sumit.multipartdemo.service;

import com.sumit.multipartdemo.dao.FileUploadRepository;
import com.sumit.multipartdemo.entity.FileUpload;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FileUploadService {
    @Autowired
    private FileUploadRepository repository;
    @Autowired
    private Tika tika;

    public String saveFileToDatabase(MultipartFile file, String metadata) {
        FileUpload fileUpload = new FileUpload();
        try {
            InputStream inputStream = file.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            System.out.println("*****************************************"+tika.detect(bytes));
            fileUpload.setFileData(bytes);
            fileUpload.setFileName(file.getOriginalFilename());
            fileUpload.setContentType(file.getContentType());
            fileUpload.setFileMetaData(metadata);
            repository.save(fileUpload);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUpload.getFileId();
    }

    public Optional<FileUpload> getUploadedFile(String fileId) {
        Optional<FileUpload> fileUploadOptional = repository.findById(fileId);
        return fileUploadOptional;
    }
}
