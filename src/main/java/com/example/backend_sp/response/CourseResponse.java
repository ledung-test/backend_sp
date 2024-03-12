package com.example.backend_sp.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse {
    Integer id;
    String name;
    String lesson_content;
    String requirements;
    String description;
    BigDecimal price;
    String url_img;
    int totalStudents;
    double rating;
    boolean activated;
    CategoryResponse category;
    List<SectionResponse> sections;

}
