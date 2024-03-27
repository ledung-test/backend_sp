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
import com.example.backend_sp.service.review.ReviewService;
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
    public Review save(ReviewRequest request) {
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
        return repository.save(review);
    }

    @Override
    public void updateReview(ReviewRequest request, Integer id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review không tồn tại"));
        Course course = courseRepository.findById(request.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (!review.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Bạn không có quyền sửa review này");
        }
        if (!review.getCourse().getId().equals(course.getId())){
            throw new RuntimeException("Review không thuộc khóa học");
        }
        review.setContent(request.getContent());
        review.setRating(request.getRating());
        repository.save(review);
    }

    @Override
    public List<ReviewResponse> getReviewByCourseId(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        List<Review> reviews = repository.findReviewByCourseId(courseId);
        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        if (!reviews.isEmpty()){
            for (Review review: reviews) {
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

    @Override
    public void deleteById(Integer id) {
        if (repository.existsReviewById(id)){
            repository.deleteById(id);
        }else {
            throw new RuntimeException("Bình luận Không tồn tại");
        }
    }
}
