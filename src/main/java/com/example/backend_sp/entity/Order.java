package com.example.backend_sp.entity;

import com.example.backend_sp.entity.enums.OrderStatus;
import com.example.backend_sp.entity.enums.PaymentMethod;
import jakarta.persistence.*;
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
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Column(precision = 10, scale = 2)
    BigDecimal totalMoney;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    Date paymentDate;
}
