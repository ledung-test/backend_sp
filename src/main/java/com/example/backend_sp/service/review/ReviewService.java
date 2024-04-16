package com.example.backend_sp.service.review;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.entity.Review;
import com.example.backend_sp.request.ReviewRequest;
import com.example.backend_sp.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    void create(ReviewRequest request);

    void update(ReviewRequest request, Integer id);

    void delete(Integer id);

    List<ReviewResponse> findAllByCourseId(Integer courseId);

    double calculatorAvgRating(Course course);
}
