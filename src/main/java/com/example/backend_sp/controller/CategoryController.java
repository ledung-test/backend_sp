package com.example.backend_sp.controller;

import com.example.backend_sp.request.CategoryRequest;
import com.example.backend_sp.response.CategoryResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
    private final CategoryService service;

    @GetMapping()
    public ResponseEntity<ResponseObject> findAllByOrderByCreatedAtDesc(){
        List<CategoryResponse> categories = service.findAllByOrderByCreatedAtDesc();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách danh mục thành công.")
                        .status(HttpStatus.OK)
                        .data(categories)
                        .build());
    }

    @GetMapping("/activated")
    public ResponseEntity<ResponseObject> findAllByActivatedTrue(){
        List<CategoryResponse> categories = service.findAllByActivatedTrue();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách danh mục thành công.")
                        .status(HttpStatus.OK)
                        .data(categories)
                        .build());
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> createCategory(
            @RequestBody @Valid CategoryRequest request){
        service.create(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới danh mục thành công.")
                        .status(HttpStatus.CREATED)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryRequest request){
        service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật danh mục thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }
}
