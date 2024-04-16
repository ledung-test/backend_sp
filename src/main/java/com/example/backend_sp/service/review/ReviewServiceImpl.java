package com.example.backend_sp.service.review;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.entity.Review;
import com.example.backend_sp.entity.User;
import com.example.backend_sp.mapper.UserMapper;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.ReviewRepository;
import com.example.backend_sp.repository.UserRepository;
import com.example.backend_sp.request.ReviewRequest;
import com.example.backend_sp.response.ReviewResponse;
import com.example.backend_sp.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public void create(ReviewRequest request) {
        Course course = courseRepository.findById(request.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .course(course)
                .user(user)
                .build();
        repository.save(review);
        course.setRating(calculatorAvgRating(course));
        courseRepository.save(course);
    }

    @Override
    public void update(ReviewRequest request, Integer id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review không tồn tại"));
        Course course = courseRepository.findById(request.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Bạn không có quyền sửa review này");
        }
        if (!review.getCourse().getId().equals(course.getId())) {
            throw new RuntimeException("Review không thuộc khóa học");
        }
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        repository.save(review);
        course.setRating(calculatorAvgRating(course));
        courseRepository.save(course);
    }

    @Override
    public void delete(Integer id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đánh giá không tồn tại"));
        Course course = courseRepository.findById(review.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        repository.delete(review);
        course.setRating(calculatorAvgRating(course));
        courseRepository.save(course);
    }


    @Override
    public List<ReviewResponse> findAllByCourseId(Integer courseId) {
        if (!courseRepository.existsCourseById(courseId)) {
            throw new RuntimeException("Khóa học không tồn tại");
        } else {
            List<Review> reviews = repository.findAllByCourseId(courseId);
            List<ReviewResponse> reviewResponseList = new ArrayList<>();
            if (!reviews.isEmpty()) {
                for (Review review : reviews) {
                    UserResponse userResponse = new UserResponse();
                    UserMapper.mapUserToUserResponse(review.getUser(), userResponse);
                    ReviewResponse reviewResponse = ReviewResponse.builder()
                            .id(review.getId())
                            .content(review.getContent())
                            .rating(review.getRating())
                            .updatedAt(review.getUpdatedAt())
                            .user(userResponse)
                            .build();
                    reviewResponseList.add(reviewResponse);
                }
            }
            return reviewResponseList;
        }
    }

    @Override
    public double calculatorAvgRating(Course course) {
        int totalRating = 0;
        List<Review> reviews = repository.findAllByCourseId(course.getId());
        Integer countReview = repository.countReviewsByCourseId(course.getId());
        for (Review review : reviews) {
            totalRating = totalRating + review.getRating();
        }
        if (countReview != 0) {
            return (double) totalRating / countReview;
        }
        return 0;
    }
}
