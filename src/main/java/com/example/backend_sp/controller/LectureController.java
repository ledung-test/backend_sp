package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.request.LectureRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.lecture.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService service;

    @PostMapping()
    public ResponseEntity<ResponseObject> insertLecture(
            @RequestBody @Valid LectureRequest request) {
        Lecture lecture = service.save(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Thêm mới bài giảng thành công")
                        .status(HttpStatus.CREATED)
                        .data(lecture)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateLecture(
            @PathVariable Integer id,
            @RequestBody @Valid LectureRequest request) {
        Lecture lecture = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật bài giảng thành công")
                        .status(HttpStatus.OK)
                        .data(lecture)
                        .build()
        );
    }

}
