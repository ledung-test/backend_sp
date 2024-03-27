package com.example.backend_sp.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Integer user_id;
    BigDecimal totalMoney;
    String paymentMethod;
    List<OrderDetailRequest> orderDetails;
}
