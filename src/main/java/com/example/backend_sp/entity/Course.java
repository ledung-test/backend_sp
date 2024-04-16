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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "courses")
public class Course extends BaseEntity{

    @Column(nullable = false)
    String name;

    String slug;

    String intro;

    @Column(columnDefinition = "TEXT")
    String requirements;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(precision = 10, scale = 2)
    BigDecimal price;

    String url_img;

    String targets;

    int totalStudents;

    double rating;

    @Column(name = "is_activated")
    boolean activated;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    Category category;
}
