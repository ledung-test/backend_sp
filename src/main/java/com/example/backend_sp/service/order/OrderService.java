package com.example.backend_sp.service.order;

import com.example.backend_sp.entity.Order;
import com.example.backend_sp.request.OrderRequest;

public interface OrderService {
    Order save(OrderRequest request);
}
