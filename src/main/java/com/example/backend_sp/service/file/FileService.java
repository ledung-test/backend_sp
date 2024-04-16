package com.example.backend_sp.service.file;

import org.springframework.web.multipart.MultipartFile;
public interface FileService {

    String uploadFile(MultipartFile file, String uploadDir);
}
