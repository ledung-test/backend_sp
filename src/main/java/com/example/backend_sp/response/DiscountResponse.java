package com.example.backend_sp.response;

import com.example.backend_sp.entity.enums.DiscountType;
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
public class DiscountResponse {

    Integer id;

    String discountName;

    DiscountType discountType;

    BigDecimal discountValue;

    Date startDate;

    Date endDate;

    Date createdAt;

    Date updatedAt;
}
