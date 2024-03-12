package com.example.backend_sp.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SectionResponse {
    Integer id;
    String name;
    Integer position;
    boolean activated;
    List<LectureResponse> lectures;
}
