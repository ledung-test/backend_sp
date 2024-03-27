package com.example.backend_sp.service.order;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.OrderDetailRepository;
import com.example.backend_sp.repository.OrderRepository;
import com.example.backend_sp.repository.UserRepository;
import com.example.backend_sp.request.OrderDetailRequest;
import com.example.backend_sp.request.OrderRequest;
import com.example.backend_sp.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    @Override
    public Order save(OrderRequest request) {
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        Order order = Order.builder()
                .user(user)
                .orderDate(new Date())
                .status(OrderStatus.PENDING)
                .totalMoney(request.getTotalMoney())
                .paymentMethod(request.getPaymentMethod())
                .build();
        repository.save(order);
        List<OrderDetailRequest> orderDetailRequests = request.getOrderDetails();
        if (!orderDetailRequests.isEmpty()){
            for (OrderDetailRequest orderDetailRequest: orderDetailRequests) {
                Course course = courseRepository.findById(orderDetailRequest.getCourse_id())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
                OrderDetail orderDetail = OrderDetail.builder()
                        .order(order)
                        .course(course)
                        .price(orderDetailRequest.getPrice())
                        .build();
                orderDetailRepository.save(orderDetail);
            }
        }
        return order;
    }
}
