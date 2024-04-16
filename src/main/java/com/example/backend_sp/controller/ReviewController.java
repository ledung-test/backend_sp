package com.example.backend_sp.controller;

import com.example.backend_sp.request.ReviewRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.response.ReviewResponse;
import com.example.backend_sp.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReviewController {
    private final ReviewService service;


    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findAllByCourseId(@PathVariable Integer id) {
        List<ReviewResponse> reviews = service.findAllByCourseId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách đánh giá thành công")
                .status(HttpStatus.OK)
                .data(reviews)
                .build());
    }


    @PostMapping()
    public ResponseEntity<ResponseObject> create(@RequestBody @Valid ReviewRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Đánh giá thành công thành công")
                .status(HttpStatus.CREATED)
                .build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid ReviewRequest request, @PathVariable Integer id) {
        service.update(request, id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Cập nhật đánh giá thành công thành công")
                .status(HttpStatus.OK)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Xóa review thành công")
                .status(HttpStatus.OK)
                .build());
    }
}
