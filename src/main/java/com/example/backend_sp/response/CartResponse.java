package com.example.backend_sp.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    List<CartItemResponse> cartItems;

    BigDecimal totalPrice;

    BigDecimal totalDiscount;

    BigDecimal totalDiscountedPrice;

    int percentDiscount;


}
