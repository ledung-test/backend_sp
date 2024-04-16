package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Order;
import com.example.backend_sp.entity.User;
import com.example.backend_sp.response.OrderResponse;
import com.example.backend_sp.response.UserResponse;

import java.util.List;

public class OrderMapper {
    public static void mapOrderToOrderResponse(Order order, OrderResponse response){
        User user = order.getUser();
        UserResponse userResponse = new UserResponse();
        UserMapper.mapUserToUserResponse(user, userResponse);
        response.setId(order.getId());
        response.setUser(userResponse);
        response.setOrderStatus(order.getOrderStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setTotalMoney(order.getTotalMoney());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdatedAt(order.getUpdatedAt());
    }

    public static void mapListOrderToListOrderResponse(List<Order> orders, List<OrderResponse> orderResponseList){
        if (!orders.isEmpty()) {
            for (Order order : orders) {
                OrderResponse orderResponse = new OrderResponse();
                OrderMapper.mapOrderToOrderResponse(order, orderResponse);
                orderResponseList.add(orderResponse);
            }
        }
    }
}
