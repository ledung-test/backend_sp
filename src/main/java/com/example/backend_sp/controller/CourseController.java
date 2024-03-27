package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.response.SectionResponse;
import com.example.backend_sp.response.TargetResponse;
import com.example.backend_sp.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {
    private final CourseService service;

    //GET: Lấy chi tiết khóa học theo ID và Slug
    @GetMapping("/{id}/{slug}")
    public ResponseEntity<ResponseObject> getCourseByIdAndSlug(
            @PathVariable Integer id,
            @PathVariable String slug) {
        CourseResponse courseResponse = service.findByIdAndSlug(id, slug);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courseResponse)
                        .build());
    }

    //GET: Lấy danh sách Sections theo Course ID và Slug
    @GetMapping("/{id}/{slug}/sections")
    public ResponseEntity<ResponseObject> getSectionByCourseByIdAndSlug(
            @PathVariable Integer id,
            @PathVariable String slug) {
        List<SectionResponse> sectionResponseList = service.findAllSectionByCourseIdAndSlug(id, slug);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách chương khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(sectionResponseList)
                        .build());
    }

    //Tìm kiếm khóa học theo nhiều tiêu chí
    @GetMapping()
    public ResponseEntity<ResponseObject> getCourseByFilter(
            @RequestParam(required = false) Double minimumRating,
            @RequestParam(required = false) String sortBy,
            @RequestParam(value = "category", required = false) List<Integer> categoryIds,
            @RequestParam(value = "price", required = false) List<String> priceOptions
    ) {
        List<CourseResponse> courseResponseList = service.getCourseByFilter(minimumRating, sortBy, categoryIds, priceOptions);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Ok")
                        .status(HttpStatus.OK)
                        .data(courseResponseList)
                        .build()
        );
    }

    //POST: Tạo mới một khóa học
    @PostMapping
    public ResponseEntity<ResponseObject> insertCourse(
            @RequestBody @Valid CourseRequest request) {
        Course course = service.save(request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới khóa học thành công")
                        .status(HttpStatus.CREATED)
                        .data(course)
                        .build()
        );
    }

    //PUT: Cập nhật khóa học
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCourse(
            @PathVariable Integer id,
            @RequestBody @Valid CourseRequest request) {
        Course course = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(course)
                        .build());
    }

    //Lấy danh sách mục tiêu khóa học theo Course ID
    @GetMapping("/targets/{id}")
    public ResponseEntity<ResponseObject> getTargetsByCourseId(@PathVariable Integer id) {
        List<TargetResponse> targets = service.getTargetsByCourseId(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Tạo mới danh mục thành công.")
                        .status(HttpStatus.CREATED)
                        .data(targets)
                        .build());
    }
}
