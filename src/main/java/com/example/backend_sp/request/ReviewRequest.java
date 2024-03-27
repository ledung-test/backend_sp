package com.example.backend_sp.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    String content;
    Integer rating;
    Integer course_id;
    Integer user_id;
}
