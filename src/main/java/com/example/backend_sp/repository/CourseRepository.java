package com.example.backend_sp.repository;


import com.example.backend_sp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

    List<Course> findAllByActivatedTrue();

    Optional<Course> findByIdAndSlug(Integer id, String slug);

    Boolean existsCourseByIdAndSlug(Integer id, String slug);


}
