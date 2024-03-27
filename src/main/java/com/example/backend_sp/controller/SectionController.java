package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Section;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.section.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

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
}
