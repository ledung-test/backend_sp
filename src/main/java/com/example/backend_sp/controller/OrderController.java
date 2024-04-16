package com.example.backend_sp.controller;

import com.example.backend_sp.request.OrderRequest;
import com.example.backend_sp.response.OrderDetailResponse;
import com.example.backend_sp.response.OrderResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    private final OrderService service;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllOrders(){
        List<OrderResponse> orders = service.getAllOrders();
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách orders thành công")
                .status(HttpStatus.OK)
                .data(orders)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable Integer id){
        OrderResponse orders = service.getOrderById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách order thành công")
                .status(HttpStatus.OK)
                .data(orders)
                .build());
    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<ResponseObject> getOrderDetailsByOrderId(@PathVariable Integer id){
        List<OrderDetailResponse> orderDetails = service.getOrderDetailsByOrderId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách order thành công")
                .status(HttpStatus.OK)
                .data(orderDetails)
                .build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseObject> getOrdersByUserId(@PathVariable Integer id){
        List<OrderResponse> orders = service.getOrdersByUserId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách orders thành công")
                .status(HttpStatus.OK)
                .data(orders)
                .build());
    }
    @PostMapping()
    public ResponseEntity<ResponseObject> create(@RequestBody @Valid OrderRequest request) {
       service.create(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm order thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody @Valid OrderRequest request, @PathVariable Integer id) {
        service.update(request, id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Cập nhật order thành công")
                .status(HttpStatus.OK)
                .build());
    }
}
