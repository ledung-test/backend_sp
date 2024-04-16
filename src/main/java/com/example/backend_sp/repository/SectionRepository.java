package com.example.backend_sp.repository;


import com.example.backend_sp.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    List<Section> findByCourseIdOrderByPositionAsc(Integer id);

    Integer countSectionByCourseId(Integer id);

    List<Section> findByCourseIdAndPositionGreaterThan(Integer courseId, Integer position);

    Optional<Section> findFirstByCourseIdAndPositionLessThanOrderByPositionDesc(Integer courseId, Integer position);

    Optional<Section> findFirstByCourseIdAndPositionGreaterThanOrderByPositionAsc(Integer courseId, Integer position);

    List<Section> findByCourseIdAndPositionLessThanEqual(Integer courseId, Integer position);
}
