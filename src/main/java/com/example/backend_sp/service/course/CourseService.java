package com.example.backend_sp.service.course;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.SectionResponse;


import java.util.List;

public interface CourseService {

    List<CourseResponse> findAll();

    List<CourseResponse> findAllByActivatedTrue();

    Course findById(Integer id);

    Course save(CourseRequest request);

    CourseResponse update(Integer id, CourseRequest request);

    CourseResponse getCourseById(Integer id);

    CourseResponse findByIdAndSlug(Integer id, String slug);

    List<SectionResponse> findAllSectionByCourseId(Integer id);

    List<CourseResponse> getCourseByFilter(Double minimumRating, String sortBy, List<Integer> categoryIds, List<String> priceOptions);

    List<CourseResponse> getCoursesByPriceFree();

    List<CourseResponse> getCoursesByRatingAndTotalStudentDesc();

    List<CourseResponse> getCoursesByDiscountNotNull();

}
