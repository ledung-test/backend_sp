package com.example.backend_sp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static final String UPLOAD_IMAGE_DIR = "image_uploads";
    public static final String UPLOAD_RESOURCES_DIR = "resources_uploads";

    public static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean result = directory.mkdirs();
            if (result) {
                System.out.println("Thư mục đã được tạo thành công: " + path);
            } else {
                System.out.println("Không thể tạo thư mục: " + path);
            }
        } else {
            System.out.println("Thư mục đã tồn tại: " + path);
        }
    }
    public static void deleteFile(String filePath) {
        filePath = filePath.substring(1);
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file");
        }
    }

    public static boolean isValidFileImgExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return false;
        }
        String lowerCaseFileName = fileName.toLowerCase();
        return lowerCaseFileName.endsWith(".png") || lowerCaseFileName.endsWith(".jpg");
    }

    public static boolean isContentTypeImg(MultipartFile file){
        String contentType = file.getContentType();
        if (contentType == null){
            return false;
        }
        return contentType.startsWith("image/");
    }

    public static boolean isValidFileImgSize(MultipartFile file) {
        long maxSizeInBytes = 4 * 1024 * 1024;
        return file.getSize() <= maxSizeInBytes;
    }
}
