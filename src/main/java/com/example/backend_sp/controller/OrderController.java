package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Order;
import com.example.backend_sp.request.OrderRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {
    private final OrderService service;
    @PostMapping()
    public ResponseEntity<ResponseObject> insertOrder(@RequestBody @Valid OrderRequest request) {
        Order order = service.save(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm order thành công")
                .status(HttpStatus.CREATED)
                .data(order)
                .build());
    }
}
