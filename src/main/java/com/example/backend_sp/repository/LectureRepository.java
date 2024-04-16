package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findBySectionIdOrderByPositionAsc(Integer id);

    List<Lecture> findBySectionId(Integer id);

    Integer countLectureBySectionId(Integer id);

    List<Lecture> findBySectionIdAndPositionGreaterThan(Integer sectionId, Integer position);
}
