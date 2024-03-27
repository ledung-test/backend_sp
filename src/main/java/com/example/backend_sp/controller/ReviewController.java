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

    //GET: Lấy danh sách review của khóa học theo Course ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getReviewByCourseId(@PathVariable Integer id) {
        List<ReviewResponse> reviews = service.getReviewByCourseId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách đánh giá thành công")
                .status(HttpStatus.OK)
                .data(reviews)
                .build());
    }

    //POST: Thêm mới một review vào khóa học
    @PostMapping()
    public ResponseEntity<ResponseObject> insertReview(@RequestBody @Valid ReviewRequest request) {
        service.save(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Đánh giá thành công thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    //PUT: Cập nhật review
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@RequestBody @Valid ReviewRequest request, @PathVariable Integer id) {
        service.updateReview(request, id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Cập nhật đánh giá thành công thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    //DELETE: Xóa review
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Xóa review thành công")
                .status(HttpStatus.OK)
                .build());
    }
}
