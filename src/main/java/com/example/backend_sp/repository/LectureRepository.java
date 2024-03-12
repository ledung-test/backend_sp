package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findBySectionId(Integer id);
}
