package com.example.backend_sp.request;

import com.example.backend_sp.entity.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountRequest {

    String discountName;
    DiscountType discountType;
    BigDecimal discountValue;
    Date startDate;
    Date endDate;
}
