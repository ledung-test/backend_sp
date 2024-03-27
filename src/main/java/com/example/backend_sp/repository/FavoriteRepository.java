package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUserId(Integer id);

    Boolean existsFavoriteById(Integer id);

    Boolean existsByUserId(Integer id);

    Boolean existsByUserIdAndCourseId(Integer userId, Integer courseId);
}
