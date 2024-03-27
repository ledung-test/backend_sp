package com.example.backend_sp.repository;

import com.example.backend_sp.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart_Id(Integer id);

    Boolean existsCartItemById(Integer id);

    Boolean existsCartItemByCart_IdAndCourse_Id(Integer cartId, Integer courseId);
}
