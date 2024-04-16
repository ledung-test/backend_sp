package com.example.backend_sp.repository;

import com.example.backend_sp.entity.CourseDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDiscountRepository extends JpaRepository<CourseDiscount, Integer> {

    Optional<CourseDiscount> findByCourseId(Integer courseId);

    Optional<CourseDiscount> findByCourseIdAndDiscountEndDateAfter(Integer courseId, Date currentDate);
    List<CourseDiscount> findByDiscountEndDateAfter(Date currentDate);

}
