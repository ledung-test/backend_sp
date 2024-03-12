package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Section;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    //GET: Lấy danh sách chương trong khóa học
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllSections(){
        List<Section> sections  = service.findAllByActivatedTrue();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách chương thành công.")
                        .status(HttpStatus.OK)
                        .data(sections)
                        .build());
    }
    //POST: Tạo mới một chương trong khóa học
    @PostMapping
    public ResponseEntity<ResponseObject> insertSection(
            @RequestBody @Valid SectionRequest request){
        Section section = service.save(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới chương thành công")
                        .status(HttpStatus.CREATED)
                        .data(section)
                        .build()
        );
    }

    //PUT: Cập nhật chương trong khóa học
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateSection(
            @PathVariable Integer id,
            @RequestBody @Valid SectionRequest request){
        Section section = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật chương thành công.")
                        .status(HttpStatus.OK)
                        .data(section)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteSection(
            @PathVariable Integer id){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Xóa chương thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }
}
