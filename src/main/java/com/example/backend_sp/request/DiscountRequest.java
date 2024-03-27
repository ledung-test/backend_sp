package com.example.backend_sp.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountRequest {

    String discountName;
    String discountType;
    double discountValue;
    Date startDate;
    Date endDate;
}
