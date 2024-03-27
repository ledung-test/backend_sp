package com.example.backend_sp.service.review;

import com.example.backend_sp.entity.Review;
import com.example.backend_sp.request.ReviewRequest;
import com.example.backend_sp.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    Review save(ReviewRequest request);

    void updateReview(ReviewRequest request, Integer id);

    List<ReviewResponse> getReviewByCourseId(Integer courseId);

    void deleteById(Integer id);
}
