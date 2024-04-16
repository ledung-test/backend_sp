package com.example.backend_sp.service.order;

import com.example.backend_sp.entity.Order;
import com.example.backend_sp.request.OrderRequest;
import com.example.backend_sp.response.OrderDetailResponse;
import com.example.backend_sp.response.OrderResponse;
import com.example.backend_sp.response.UserResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface OrderService {
    void create(OrderRequest request);

    void update(OrderRequest request, Integer id);

    void saveOrderDetails(Order order, Set<Integer> listCourseId);

    List<OrderResponse> getOrdersByUserId(Integer userId);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Integer id);

    List<OrderDetailResponse> getOrderDetailsByOrderId(Integer orderId);

    BigDecimal calculatorTotalPrice(Order order);

}
