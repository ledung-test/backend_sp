package com.example.backend_sp.entity;

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
public class Order extends BaseEntity{

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    Date orderDate;

    String status;

    @Column(precision = 10, scale = 2) //DECIMAL(10,2)
    BigDecimal totalMoney;

    String paymentMethod;

    Date paymentDate;
}
