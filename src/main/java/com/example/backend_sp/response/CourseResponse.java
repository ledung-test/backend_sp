package com.example.backend_sp.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse {
    Integer id;
    String name;
    String slug;
    String intro;
    String lesson_content;
    String requirements;
    String description;
    BigDecimal price;
    BigDecimal discount;
    String url_img;
    String targets;
    int totalStudents;
    double rating;
    boolean activated;
    CategoryResponse category;

    Integer discount_id;
}
