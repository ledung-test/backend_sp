package com.example.backend_sp.service;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    List<Course> findAllByActivatedTrue();

    Course findById(Integer id);

    void mapCourseRequestToCourse(CourseRequest request, Course course);

    Course save(CourseRequest request);

    Course update(Integer id, CourseRequest request);

    void deleteById(Integer id);

    CourseResponse findByIdAndSlug(Integer id, String slug);


}
