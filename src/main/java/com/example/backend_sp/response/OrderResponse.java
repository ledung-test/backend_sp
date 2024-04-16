package com.example.backend_sp.response;

import com.example.backend_sp.entity.enums.OrderStatus;
import com.example.backend_sp.entity.enums.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;

    UserResponse user;

    OrderStatus orderStatus;

    PaymentMethod paymentMethod;

    BigDecimal totalMoney;

    Date createdAt;

    Date updatedAt;

}
