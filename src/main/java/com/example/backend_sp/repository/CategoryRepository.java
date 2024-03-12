package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByNameAndActivatedTrue(String name);
    List<Category> findAllByActivatedTrue();
}
