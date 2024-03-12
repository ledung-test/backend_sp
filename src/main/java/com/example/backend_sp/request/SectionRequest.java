package com.example.backend_sp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionRequest {

    @NotBlank(message = "Tên chương không được để trống")
    @Size(min = 3, max = 255, message = "Tên chủ đề phải dài từ 3 đến 255 ký tự")
    String name;

    Integer position;

    boolean activated;

    Integer course_id;
}
