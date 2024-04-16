package com.example.backend_sp.service.cart;


import com.example.backend_sp.entity.CartItem;
import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.response.CartItemResponse;
import com.example.backend_sp.response.CartResponse;
import com.example.backend_sp.response.CourseResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    CartResponse getCoursesFromCart(Integer id);

    BigDecimal getTotalPrice(List<CartItem> cartItems);

    BigDecimal getTotalDiscountedPrice(BigDecimal totalPrice, BigDecimal totalDiscount);

    int getPercentDiscount(BigDecimal totalPrice, BigDecimal totalDiscount);

    CartItem save(CartItemRequest request);

    void deleteById(Integer id);

    Integer getIdCartByUserId(Integer userId);


}
