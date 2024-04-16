package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.response.SectionResponse;
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

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllCourses() {
        List<CourseResponse> courses = service.findAll();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courses)
                        .build());
    }

    //GET: Lấy tất cả khóa học Activated = true;
    @GetMapping("/activated")
    public ResponseEntity<ResponseObject> getAllCoursesByActivatedTrue() {
        List<CourseResponse> courseResponse = service.findAllByActivatedTrue();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courseResponse)
                        .build());
    }

    //GET: Lấy chi tiết khóa học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCourseByIdAndSlug(
            @PathVariable Integer id) {
        CourseResponse courseResponse = service.getCourseById(id);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courseResponse)
                        .build());
    }

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
    @GetMapping("/{id}/sections")
    public ResponseEntity<ResponseObject> getSectionByCourseById(
            @PathVariable Integer id) {
        List<SectionResponse> sectionResponseList = service.findAllSectionByCourseId(id);
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

    @GetMapping("/free")
    public ResponseEntity<ResponseObject> getCoursesByPriceFree(){
        List<CourseResponse> courseResponseList = service.getCoursesByPriceFree();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Ok")
                        .status(HttpStatus.OK)
                        .data(courseResponseList)
                        .build()
        );
    }

    @GetMapping("/discount")
    public ResponseEntity<ResponseObject> getCoursesByDiscountNotNull(){
        List<CourseResponse> courseResponseList = service.getCoursesByDiscountNotNull();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Ok")
                        .status(HttpStatus.OK)
                        .data(courseResponseList)
                        .build()
        );
    }

    @GetMapping("/rating")
    public ResponseEntity<ResponseObject> getCoursesByRatingAndTotalStudentDesc(){
        List<CourseResponse> courseResponseList = service.getCoursesByRatingAndTotalStudentDesc();
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
            @ModelAttribute CourseRequest request) {
        CourseResponse course = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(course)
                        .build());
    }
}
