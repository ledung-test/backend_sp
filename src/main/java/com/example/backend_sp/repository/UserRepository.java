package com.example.backend_sp.repository;

import com.example.backend_sp.entity.enums.Role;
import com.example.backend_sp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(Role role);

    Boolean existsUserById(Integer id);
}
