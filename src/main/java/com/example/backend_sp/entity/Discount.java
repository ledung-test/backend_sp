package com.example.backend_sp.entity;

import com.example.backend_sp.entity.enums.DiscountType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {
    String discountName;
    @Enumerated(EnumType.STRING)
    DiscountType discountType;
    BigDecimal discountValue;
    Date startDate;
    Date endDate;
}
