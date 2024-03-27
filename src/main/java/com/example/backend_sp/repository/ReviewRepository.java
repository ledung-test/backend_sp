package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewByCourseId(Integer id);

    Boolean existsReviewById(Integer id);
}
