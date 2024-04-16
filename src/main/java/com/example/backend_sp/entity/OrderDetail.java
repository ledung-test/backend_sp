package com.example.backend_sp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @Column(precision = 10, scale = 2)
    BigDecimal price;

    @Column(precision = 10, scale = 2)
    BigDecimal discount;


}
