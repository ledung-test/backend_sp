package com.example.backend_sp.repository;

import com.example.backend_sp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Boolean existsCartByUserId(Integer id);
    Cart findByUserId(Integer id);
}
