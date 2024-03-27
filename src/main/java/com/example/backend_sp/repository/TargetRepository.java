package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {
    List<Target> findByCourse_Id(Integer id);
}
