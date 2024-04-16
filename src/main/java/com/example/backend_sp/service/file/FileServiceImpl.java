package com.example.backend_sp.service.file;

import com.example.backend_sp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{


    public FileServiceImpl() {
        FileUtils.createDirectory(FileUtils.UPLOAD_IMAGE_DIR);
        FileUtils.createDirectory(FileUtils.UPLOAD_RESOURCES_DIR);
    }

    @Override
    public String uploadFile(MultipartFile file, String uploadDir){
        if (file.isEmpty()) {
            throw new RuntimeException("Không có tệp để tải lên");
        }
        try {
            String fileName = UUID.randomUUID().toString();
            Path path = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), path);
            return File.separator + uploadDir + File.separator + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Không thể tải tệp lên");
        }
    }
}
