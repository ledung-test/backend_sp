package com.example.backend_sp.repository;

import com.example.backend_sp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCartId(Integer id);

    Boolean existsCartItemById(Integer id);

    Boolean existsCartItemByCartIdAndCourseId(Integer cartId, Integer courseId);
}
