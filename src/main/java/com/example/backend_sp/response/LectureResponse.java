package com.example.backend_sp.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureResponse {

    Integer id;

    String name;

    String video_url;

    String document_url;

    Integer position;

    boolean activated;
}
