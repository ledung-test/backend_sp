package com.example.backend_sp.controller;

import com.example.backend_sp.request.CartItemRequest;
import com.example.backend_sp.response.CartItemResponse;
import com.example.backend_sp.response.CartResponse;
import com.example.backend_sp.response.ResponseObject;
import com.example.backend_sp.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {
    private final CartService service;

    //GET: Lấy danh sách khóa học trong giỏ hàng theo User ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCoursesFromCart(@PathVariable Integer id) {
        CartResponse cart = service.getCoursesFromCart(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy giỏ hàng thành công")
                .status(HttpStatus.OK)
                .data(cart)
                .build());
    }

    //GET: Lấy ID Cart theo User ID
    @GetMapping("/cart_id/{id}")
    public ResponseEntity<ResponseObject> getIdCartByUserId(@PathVariable Integer id) {
        Integer cartId = service.getIdCartByUserId(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Lấy ID giỏ hàng thành công")
                .status(HttpStatus.OK)
                .data(cartId)
                .build());
    }

    //POST: Thêm mới một khóa học vào giỏ hàng
    @PostMapping("/cart_items")
    public ResponseEntity<ResponseObject> insertCartItem(@RequestBody @Valid CartItemRequest request) {
        service.save(request);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Thêm vào giỏ hàng thành công")
                .status(HttpStatus.CREATED)
                .build());
    }

    //DELETE: Xóa một khóa học khỏi giỏ hàng
    @DeleteMapping("/cart_items/{id}")
    public ResponseEntity<ResponseObject> deleteCartItem(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Xóa khỏi giỏ hàng thành công")
                .status(HttpStatus.OK)
                .build());
    }
}
