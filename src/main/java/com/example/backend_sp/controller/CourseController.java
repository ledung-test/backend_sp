package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService service;

    //GET: Lấy danhh sách khóa học
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllCourses(){
        List<Course> courses = service.findAllByActivatedTrue();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy danh sách khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courses)
                        .build());
    }

    //GET: Lấy chi tiết khóa học theo id và slug
    @GetMapping("/{id}/{slug}")
    public ResponseEntity<ResponseObject> getCourseByIdAndSlug(
            @PathVariable Integer id,
            @PathVariable String slug){
        CourseResponse courseResponse = service.findByIdAndSlug(id, slug);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Lấy khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(courseResponse)
                        .build());
    }

    //POST: Tạo mới một khóa học
    @PostMapping
    public ResponseEntity<ResponseObject> insertCourse(
            @RequestBody @Valid CourseRequest request){
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
            @RequestBody @Valid CourseRequest request){
        Course course = service.update(id, request);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Cập nhật khóa học thành công.")
                        .status(HttpStatus.OK)
                        .data(course)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCourse(
            @PathVariable Integer id){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Xóa khóa học thành công.")
                        .status(HttpStatus.OK)
                        .build());
    }
}
