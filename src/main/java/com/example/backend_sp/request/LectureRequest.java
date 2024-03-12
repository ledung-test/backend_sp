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
public class LectureRequest {

    @NotBlank(message = "Tên bài hộc không được để trống")
    @Size(min = 3, max = 255, message = "Tên bài học phải dài từ 3 đến 255 ký tự")
    String name;

    String video_url;

    String document_url;

    Integer position;

    boolean activated;

    Integer section_id;

}
