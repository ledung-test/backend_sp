package com.example.backend_sp.controller;

import com.example.backend_sp.entity.Discount;
import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.request.DiscountRequest;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.discount.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DiscountController {

    private final DiscountService service;
    @PostMapping()
    public ResponseEntity<ResponseObject> createDiscount(@RequestBody @Valid DiscountRequest request) {
        Discount discount = service.create(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm mã giảm giá thành công")
                .status(HttpStatus.CREATED)
                .data(discount)
                .build());
    }
}
