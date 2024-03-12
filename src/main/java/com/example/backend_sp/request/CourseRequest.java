package com.example.backend_sp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseRequest {
    @NotBlank(message = "Tên khóa học không được để trống")
    @Size(min = 3, max = 255, message = "Tên khóa học phải dài từ 3 đến 255 ký tự")
    String name;

    String lesson_content;

    String requirements;

    String description;

    BigDecimal price;

    String url_img;

    boolean activated;

    Integer category_id;
}
