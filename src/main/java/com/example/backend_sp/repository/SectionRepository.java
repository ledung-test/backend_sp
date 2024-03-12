package com.example.backend_sp.repository;


import com.example.backend_sp.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    List<Section> findAllByActivatedTrue();

    List<Section> findByCourse_Id(Integer id);
}
