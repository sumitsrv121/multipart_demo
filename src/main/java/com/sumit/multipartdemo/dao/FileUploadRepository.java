package com.sumit.multipartdemo.dao;

import com.sumit.multipartdemo.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, String> {
}
