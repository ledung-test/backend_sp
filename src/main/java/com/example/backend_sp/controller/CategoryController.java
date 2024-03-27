package com.example.backend_sp.controller;


import com.example.backend_sp.entity.Category;
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

    //GET: Lấy danh sách danh mục
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllCategories(){
        List<CategoryResponse> categories = service.findAllByActivatedTrue();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách danh mục thành công.")
                        .status(HttpStatus.OK)
                        .data(categories)
                        .build());
    }
    //POST: Tạo mới danh mục
    @PostMapping()
    public ResponseEntity<ResponseObject> insertCategory(
            @RequestBody @Valid CategoryRequest request){
        Category category = service.save(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới danh mục thành công.")
                        .status(HttpStatus.CREATED)
                        .data(category)
                        .build());
    }
    //Put: Cập nhật danh mục
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryRequest request){
        Category category = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật danh mục thành công.")
                        .status(HttpStatus.OK)
                        .data(category)
                        .build());
    }
    //DELETE: Xóa danh mục
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable Integer id){
        service.deleteById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Xóa danh mục thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }

}
