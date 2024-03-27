package com.example.backend_sp.repository;

import com.example.backend_sp.entity.CourseDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDiscountRepository extends JpaRepository<CourseDiscount, Integer> {
}
