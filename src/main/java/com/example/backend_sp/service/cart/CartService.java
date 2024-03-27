package com.example.backend_sp.service.cart;


import com.example.backend_sp.entity.CartItem;
import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.response.CartItemResponse;

import java.util.List;

public interface CartService {
    List<CartItemResponse> getCoursesFromCart(Integer id);

    CartItem save(CartItemRequest request);

    void deleteById(Integer id);

    Integer getIdCartByUserId(Integer userId);
}
