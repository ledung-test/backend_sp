package com.example.backend_sp.controller;

import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.request.UpdatePositionRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.section.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService service;

    @PostMapping
    public ResponseEntity<ResponseObject> create(
            @RequestBody @Valid SectionRequest request) {
        service.create(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới chương thành công")
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(
            @PathVariable Integer id,
            @RequestBody @Valid SectionRequest request) {
        service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật chương thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }

    @PutMapping("/position")
    public ResponseEntity<ResponseObject> updatePosition(
            @RequestBody @Valid List<UpdatePositionRequest> request) {
        service.updatePosition(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật chương thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(
            @PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Xóa bài chương thành công")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
