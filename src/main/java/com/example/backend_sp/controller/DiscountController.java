package com.example.backend_sp.controller;

import com.example.backend_sp.request.DiscountRequest;
import com.example.backend_sp.response.DiscountResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.discount.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DiscountController {

    private final DiscountService service;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllDiscounts() {
        List<DiscountResponse> discounts = service.getAllDiscounts();
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách mã giảm giá thành công")
                .status(HttpStatus.OK)
                .data(discounts)
                .build());
    }

    @GetMapping("/activated")
    public ResponseEntity<ResponseObject> getAllDiscountsByActivatedTrue() {
        List<DiscountResponse> discounts = service.getAllDiscountsByActivatedTrue();
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy danh sách mã giảm giá thành công")
                .status(HttpStatus.OK)
                .data(discounts)
                .build());
    }

    @PostMapping()
    public ResponseEntity<ResponseObject> createDiscount(@RequestBody @Valid DiscountRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm mã giảm giá thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateDiscount(@RequestBody @Valid DiscountRequest request, @PathVariable Integer id) {
       service.update(request, id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Cập nhật giảm giá thành công")
                .status(HttpStatus.OK)
                .build());
    }
}
