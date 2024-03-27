package com.example.backend_sp.service.course;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.SectionResponse;
import com.example.backend_sp.response.TargetResponse;

import java.util.List;
import java.util.Map;

public interface CourseService {

    List<Course> findAll();

    List<CourseResponse> findAllByActivatedTrue();

    Course findById(Integer id);

    Course save(CourseRequest request);

    Course update(Integer id, CourseRequest request);

    CourseResponse findByIdAndSlug(Integer id, String slug);

    List<SectionResponse> findAllSectionByCourseIdAndSlug(Integer id, String slug);

    List<CourseResponse> getCourseByFilter(Double minimumRating, String sortBy, List<Integer> categoryIds, List<String> priceOptions);

    List<TargetResponse> getTargetsByCourseId(Integer courseId);
}
