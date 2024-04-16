package com.example.backend_sp.service.order;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.entity.enums.OrderStatus;
import com.example.backend_sp.entity.enums.PaymentMethod;
import com.example.backend_sp.mapper.CourseMapper;
import com.example.backend_sp.mapper.OrderMapper;
import com.example.backend_sp.repository.*;
import com.example.backend_sp.request.OrderRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.OrderDetailResponse;
import com.example.backend_sp.response.OrderResponse;
import com.example.backend_sp.utils.DiscountUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseDiscountRepository courseDiscountRepository;

    @Override
    public void create(OrderRequest request) {
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        Order order = Order.builder()
                .user(user)
                .build();
        if (request.getPaymentMethod().equals("BANK_TRANSFER")){
            order.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
            order.setOrderStatus(OrderStatus.PENDING);
        }
        repository.save(order);
        Set<Integer> listCourseId = request.getListCourseId();
        saveOrderDetails(order, listCourseId);
        repository.save(order);
        order.setTotalMoney(calculatorTotalPrice(order));
        repository.save(order);
    }

    @Override
    public void update(OrderRequest request, Integer id) {
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        existingOrder.setUser(user);
        switch (request.getPaymentMethod()){
            case "BANK_TRANSFER" -> existingOrder.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
            case "VNPAY" -> existingOrder.setPaymentMethod(PaymentMethod.VNPAY);
            default -> throw new RuntimeException("Phương thức thanh toán không hợp lệ");
        }
        switch (request.getOrderStatus()) {
            case "PENDING" -> existingOrder.setOrderStatus(OrderStatus.PENDING);
            case "COMPLETE" -> existingOrder.setOrderStatus(OrderStatus.COMPLETE);
            case "CANCELLED" -> existingOrder.setOrderStatus(OrderStatus.CANCELLED);
            default -> throw new RuntimeException("Trạng thái đơn hàng không hợp lệ");
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(existingOrder.getId());
        Set<Integer> listCourseId = request.getListCourseId();
        if (!orderDetailList.isEmpty()){
            orderDetailRepository.deleteAll(orderDetailList);
            for (OrderDetail orderDetail : orderDetailList) {
                int courseId = orderDetail.getCourse().getId();
                if (listCourseId.contains(courseId)) {
                    listCourseId.remove(courseId);
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        if (!listCourseId.isEmpty()){
            saveOrderDetails(existingOrder, listCourseId);
        }
        existingOrder.setTotalMoney(calculatorTotalPrice(existingOrder));
        repository.save(existingOrder);
    }

    @Override
    public void saveOrderDetails(Order order, Set<Integer> listCourseId) {
        if (!listCourseId.isEmpty()) {
            for (Integer courseId : listCourseId) {
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
                BigDecimal discount = DiscountUtils.getDiscountFromCourse(courseDiscountRepository, course);
                OrderDetail orderDetail = OrderDetail.builder()
                        .order(order)
                        .course(course)
                        .price(course.getPrice())
                        .build();
                if (Objects.equals(discount, BigDecimal.ZERO)){
                    orderDetail.setDiscount(null);
                }else {
                    orderDetail.setDiscount(discount);
                }
                orderDetailRepository.save(orderDetail);
            }
        }
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Integer userId) {
        if (userRepository.existsUserById(userId)){
            List<Order> orders = repository.findAllByUserId(userId);
            List<OrderResponse> orderResponseList = new ArrayList<>();
            OrderMapper.mapListOrderToListOrderResponse(orders, orderResponseList);
            return orderResponseList;
        }else {
            throw new RuntimeException("User không tồn tại");
        }
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = repository.findAll();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        OrderMapper.mapListOrderToListOrderResponse(orders, orderResponseList);
        return orderResponseList;
    }

    @Override
    public OrderResponse getOrderById(Integer id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        OrderResponse orderResponse = new OrderResponse();
        OrderMapper.mapOrderToOrderResponse(order, orderResponse);
        return orderResponse;
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(Integer orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        if (!orderDetails.isEmpty()) {
            for (OrderDetail orderDetail : orderDetails) {
                Course course = orderDetail.getCourse();
                CourseResponse courseResponse = new CourseResponse();
                CourseMapper.mapCourseToCourseResponse(course, courseResponse);
                courseResponse.setDiscount(orderDetail.getDiscount());
                OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                        .id(orderDetail.getId())
                        .course(courseResponse)
                        .price(orderDetail.getPrice())
                        .discount(orderDetail.getDiscount())
                        .build();
                orderDetailResponseList.add(orderDetailResponse);
            }
        }
        return orderDetailResponseList;
    }

    @Override
    public BigDecimal calculatorTotalPrice(Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(order.getId());
        if (!orderDetails.isEmpty()){
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getDiscount() != null){
                    totalPrice = totalPrice.add(orderDetail.getDiscount());
                }else {
                    totalPrice = totalPrice.add(orderDetail.getPrice());
                }
            }
        }
        return totalPrice;
    }

}
